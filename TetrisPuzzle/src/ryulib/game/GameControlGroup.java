package ryulib.game;

import ryulib.OnNotifyEventListener;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameControlGroup extends GameControlBase {

	public GameControlGroup(GameControlGroup gameControlGroup) {
		super(gameControlGroup);

	}

	protected GameControlList _GameControlList = new GameControlList();
	
	public final void clearControls() {
		_GameControlList.clear();
	}
	
	public final void addControl(GameControlBase control) {
		control.setGameControlGroup(this);
		_GameControlList.add(control);
	}

	public final void removeControl(GameControlBase control) {
		_GameControlList.remove(control);
		control.setGameControlGroup(null);
	}
	
	public final void bringToFront(GameControlBase control) {
		_GameControlList.bringToFront(control);
	}
	
	public final void sendToBack(GameControlBase control) {
		_GameControlList.sendToBack(control);
	}

	public final GameControl checkCollision(GameControl target) {
		return _GameControlList.checkCollision(target);
	}
	
	public final void checkCollision(GameControl target, OnNotifyEventListener event) {
		_GameControlList.checkCollision(target, event);
	}
	
	final void arrangeControls() {
		_GameControlList.arrangeControls();
	}
	
	@Override
	final void onRepeate(GamePlatformInfo platformInfo) {
		if (getDeleted()) return; 
			
		if (_IsStarted == false) {
			_IsStarted = true;
			onStart(platformInfo);
		}
			
		if (getEnabled()) {
			onTick(platformInfo);
			
			if (getVisible()) {
				onDraw(platformInfo);
				_GameControlList.onRepeate(platformInfo);
			}
		}
	}
	
	protected boolean doKeyDown(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		return false;
	}
	
	@Override
	protected final boolean onKeyDown(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		if (_GameControlList.onKeyDown(platformInfo, keyCode, msg) == false) return doKeyDown(platformInfo, keyCode, msg);
		return true;
    }

	protected boolean doKeyUp(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		return false;
	}

	@Override
	protected final boolean onKeyUp(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {		
    	if (_GameControlList.onKeyUp(platformInfo, keyCode, msg) == false) return doKeyUp(platformInfo, keyCode, msg);
		return true;
    }

	protected boolean doTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
		return false;
	}
	
	@Override
	protected final boolean onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
    	if (_GameControlList.onTouchEvent(platformInfo, event) == false) return doTouchEvent(platformInfo, event);
		return true;
    }
	
}
