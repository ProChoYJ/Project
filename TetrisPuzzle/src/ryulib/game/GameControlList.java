package ryulib.game;

import java.util.ArrayList;

import ryulib.OnNotifyEventListener;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameControlList {
	
	private ArrayList<GameControlBase> _List = new ArrayList<GameControlBase>();
	
	// 바로 처리 할 수 없는 것들을 대기 시킨다.  루프 도는 중에 리스트 변경을 방지하기 위해서
	private ArrayList<GameControlBase> _QueAdd = new ArrayList<GameControlBase>();
	private ArrayList<GameControlBase> _QueRemove = new ArrayList<GameControlBase>();
	private ArrayList<GameControlBase> _QueSendToBack = new ArrayList<GameControlBase>();
	private ArrayList<GameControlBase> _QueBringToFront = new ArrayList<GameControlBase>();
	
	public final void clear() {
		synchronized (_List) {
			for (GameControlBase control : _List) {
				_QueRemove.add(control);
			}
		}
	}
	
	public final void add(GameControlBase control) {
		synchronized (_QueAdd) {
			_QueAdd.add(control);
		}
	}
	
	public final void remove(GameControlBase control) {
		synchronized (_QueRemove) {
			_QueRemove.add(control);
		}
	}
	
	public final void bringToFront(GameControlBase control) {
		synchronized (_QueBringToFront) {
			_QueBringToFront.add(control);
		}
	}
	
	public final void sendToBack(GameControlBase control) {
		synchronized (_QueSendToBack) {
			_QueSendToBack.add(control);
		}
	}

	public final GameControl checkCollision(GameControl target) {
		synchronized (_List) {
			for (GameControlBase control : _List) {
				if (control instanceof GameControlGroup) continue;
				
				HitArea _HitArea = control.getHitArea();
				
				if ((isControlVisible(control) == false) || (_HitArea == null) || (control == target)) continue;
				
				if (target.getHitArea().checkCollision(_HitArea)) {
					return (GameControl) control;
				}
			}
		}

		return null;
	}
	
	public final void checkCollision(GameControl target, OnNotifyEventListener event) {
		synchronized (_List) {
			for (GameControlBase control : _List) {
				if (control instanceof GameControlGroup) continue;
				
				HitArea _HitArea = control.getHitArea();
				
				if ((isControlVisible(control) == false) || (_HitArea == null) || (control == target)) continue;
				
				if (target.getHitArea().checkCollision(_HitArea)) {
					event.onNotify(control);
				}
			}
		}
	}
	
	public final void onRepeate(GamePlatformInfo platformInfo) {
		// 반복문 사이에 락이 걸리는 것을 최소화 하기 위하여 
		_GameControlIndex = 0;
		GameControlBase control = getGameControl();
		while (control != null) {
			control.onRepeate(platformInfo);
			control = getGameControl();
		}
	}
	
	public final boolean onKeyDown(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		synchronized (_List) {
			for (int i=_List.size()-1; i>=0; i--) {
				GameControlBase control = _List.get(i);
				if (isControlAvailable(control) == false) continue;

				if (control.onKeyDown(platformInfo, keyCode, msg)) return true;
			}
			
			return false;
		}
    }

	public final boolean onKeyUp(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		synchronized (_List) {
			for (int i=_List.size()-1; i>=0; i--) {
				GameControlBase control = _List.get(i);
				if (isControlAvailable(control) == false) continue;
				
				if (control.onKeyUp(platformInfo, keyCode, msg)) return true;
			}
		}
		
		return false;
    }
    
	public final boolean onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
		boolean result = false;
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: 
				result = do_ActionDown(platformInfo, event);
				break;
				
			case MotionEvent.ACTION_UP: 
				result = do_ActionUp(platformInfo, event);
				break;
				
			default: 
				result = do_ActionOthers(platformInfo, event);
				break;
		}
		
		return result;
	}
	
	// 삭제된 객체를 지우고, 등록대기 객체를 리스트에 추가 그리고 BringToFront, SendToBack을 시행한다.
	public final void arrangeControls() {
		synchronized (_List) {
			for (int i=_List.size()-1; i>=0; i--) {
				GameControlBase control = _List.get(i);

				if (control._Deleted) {
					_List.remove(i);
					continue;
				} 
				
				if (control instanceof GameControlGroup) {
					((GameControlGroup) control).arrangeControls();
				}
			}
			
			synchronized (_QueRemove) {
				for (GameControlBase control : _QueRemove) {
					_List.remove(control);
				}
				_QueRemove.clear();
			}

			synchronized (_QueAdd) {
				for (GameControlBase control : _QueAdd) {
					_List.add(control);
				}
				_QueAdd.clear();
			}

			synchronized (_QueBringToFront) {
				for (GameControlBase control : _QueBringToFront) {
					_List.remove(control);
					_List.add(control);
				}
				_QueBringToFront.clear();
			}

			synchronized (_QueSendToBack) {
				for (GameControlBase control : _QueSendToBack) {
					_List.remove(control);
					_List.add(0, control);
				}
				_QueSendToBack.clear();
			}
		}
	}
	
	private int _GameControlIndex = 0;
	
	private GameControlBase getGameControl() {
		synchronized (_List) {
			if (_List.size() == 0) return null;

			if (_GameControlIndex >= _List.size()) {
				_GameControlIndex = 0;
				return null;
			}
			
			GameControlBase control = _List.get(_GameControlIndex);
			_GameControlIndex++;
			
			return control;
		}		
	}
	
	private GameControlBase _FocusedObject = null;
	
	private boolean canHaveFocus(MotionEvent event, GameControlBase control) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		return 
			((_FocusedObject == null) || ((_FocusedObject != null) && (_FocusedObject._Deleted))) &&
			(control.getBoundary().isMyArea(x, y));
	}
	
	private boolean do_ActionDown(GamePlatformInfo platformInfo, MotionEvent event) {
		synchronized (_List) {		
			for (int i=_List.size()-1; i>=0; i--) {
				GameControlBase control = _List.get(i);
				if (isControlVisible(control) == false) continue;
				
				if (canHaveFocus(event, control)) {
					_FocusedObject = control;
					control._Focused = true;
				}
				
				if (control.onTouchEvent(platformInfo, event)) return true;
			}
			
			return false;
		}		
	}
	
	private boolean do_ActionUp(GamePlatformInfo platformInfo, MotionEvent event) {
		boolean result = false;
		
		synchronized (_List) {		
			for (int i=_List.size()-1; i>=0; i--) {
				GameControlBase control = _List.get(i);
				if (isControlVisible(control) == false) continue;

				if (control.onTouchEvent(platformInfo, event)) {
					result = true;
					break;
				}
			}

			// ACTION_UP이면 오브젝트가 포커스를 가질 수가 없다.
			if (_FocusedObject != null) _FocusedObject._Focused = false;
			_FocusedObject = null;
			
			return result;
		}		
	}
	
	private boolean do_ActionOthers(GamePlatformInfo platformInfo, MotionEvent event) {
		synchronized (_List) {		
			for (int i=_List.size()-1; i>=0; i--) {
				GameControlBase control = _List.get(i);
				if (isControlVisible(control) == false) continue;

				if (control.onTouchEvent(platformInfo, event)) return true;
			}
			
			return false;
		}		
	}
	
	private boolean isControlAvailable(GameControlBase control) {
		return (control._Deleted == false) && (control.getVisible()) && (control.getEnabled());
	}

	private boolean isControlVisible(GameControlBase control) {
		return (control._Deleted == false) && (control.getVisible());
	}
	
}
