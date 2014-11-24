package ryulib.game;

import android.view.KeyEvent;
import android.view.MotionEvent;
import ryulib.graphic.Boundary;

public class GameControlBase {
	
	public GameControlBase(GameControlGroup gameControlGroup) {
		super();
		
		setGameControlGroup(gameControlGroup);
	}

	GameControlGroup _GameControlGroup = null;
	
	final void setGameControlGroup(GameControlGroup value) {
		onGameControlGroupChanged(value);
		_GameControlGroup = value;
	}
	
	// GameControlList에서 바로 삭제 할 수 없고, 
	// 플래그를 세팅하면 GameControlList가 적당한 시기에 삭제한다. 
	boolean _Deleted = false;

	// MotionEvent.ACTION_DOWN으로 선택된  객체일 때 true
	boolean _Focused = false;
	
	// 최초로 onRepeate가 실행되는 순간을 따로 처리하기 위함 (초기화)
	boolean _IsStarted = false;
	
	private Boundary _Boundary = new Boundary(0, 0, 0, 0);
	private boolean _Visible = true;
	private boolean _Enabled = true;
	
	void onRepeate(GamePlatformInfo platformInfo) {
		if (getDeleted()) return; 

		if (_IsStarted == false) {
			_IsStarted = true;
			onStart(platformInfo);
		}
		
		if ((_Deleted == false) && _Enabled && _Visible) onTick(platformInfo);
		if ((_Deleted == false) && _Visible) onDraw(platformInfo);
	}
	
	// 충돌 테스트를 위한 영역.  
	// 컨트롤의 크기보다 작은 영역에서만 충돌을 감지해야 할 경우가 많기 때문에 _Boundary와 따로 처리한다.
	// 예를 들어서 비행기와 같은 이미지는 정확히 사각형이 아니기 때문에 가장자리 빈공간에서 부디치는 어색함이 생길 수 있다.
	// null로 지정되어 있으면 충돌 테스트를 하지 않게 되며, 충돌 테스트가 필요한 경우 객체를 할당하면 된다. 
	protected HitArea getHitArea() {
		return null;
	}
	
	protected void onGameControlGroupChanged(GameControlGroup gameControlGroup) {
		// Override 해서 사용
	}
	
	protected void onStart(GamePlatformInfo platformInfo) {
		// Override 해서 사용
	}
	
	protected void onTick(GamePlatformInfo platformInfo) {
		// Override 해서 사용
	}
	
	protected void onDraw(GamePlatformInfo platformInfo) {
		// Override 해서 사용
	}
	
	protected boolean onKeyDown(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		// Override 해서 사용
		return false;
    }

    protected boolean onKeyUp(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		// Override 해서 사용
		return false;
    }

    protected boolean onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
		// Override 해서 사용
		return false;
    }
    
	public final void bringToFront() {
		if (_GameControlGroup != null)
			_GameControlGroup.bringToFront(this);
	}
	
	public final void sendToBack() {
		if (_GameControlGroup != null)
			_GameControlGroup.sendToBack(this);
	}
	
	public final GameControlGroup getGameControlGroup() {
		return _GameControlGroup;
	}

	public final Boundary getBoundary() {
		return _Boundary;
	}

	public final void setBoundary(Boundary value) {
		_Boundary = value;
	}
    
    public final boolean getVisible() {
    	return _Visible;
    }
    
    public final void setVisible(boolean value) {
    	_Visible = value;
    }
    
    public final boolean getEnabled() {
    	return _Enabled;
    }
    
    public final void setEnabled(boolean value) {
    	_Enabled = value;
    }

	public boolean isStarted() {
		return _IsStarted;
	}

	public boolean getFocused() {
		return _Focused;
	}
	
	public boolean getDeleted() {
		return _Deleted;
	}
	
	public void delete() {
		setVisible(false);
		setEnabled(false);		
		_Deleted = true;
	}

}
