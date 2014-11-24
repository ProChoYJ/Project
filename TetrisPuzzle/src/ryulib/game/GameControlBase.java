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
	
	// GameControlList���� �ٷ� ���� �� �� ����, 
	// �÷��׸� �����ϸ� GameControlList�� ������ �ñ⿡ �����Ѵ�. 
	boolean _Deleted = false;

	// MotionEvent.ACTION_DOWN���� ���õ�  ��ü�� �� true
	boolean _Focused = false;
	
	// ���ʷ� onRepeate�� ����Ǵ� ������ ���� ó���ϱ� ���� (�ʱ�ȭ)
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
	
	// �浹 �׽�Ʈ�� ���� ����.  
	// ��Ʈ���� ũ�⺸�� ���� ���������� �浹�� �����ؾ� �� ��찡 ���� ������ _Boundary�� ���� ó���Ѵ�.
	// ���� �� ������ ���� �̹����� ��Ȯ�� �簢���� �ƴϱ� ������ �����ڸ� ��������� �ε�ġ�� ������� ���� �� �ִ�.
	// null�� �����Ǿ� ������ �浹 �׽�Ʈ�� ���� �ʰ� �Ǹ�, �浹 �׽�Ʈ�� �ʿ��� ��� ��ü�� �Ҵ��ϸ� �ȴ�. 
	protected HitArea getHitArea() {
		return null;
	}
	
	protected void onGameControlGroupChanged(GameControlGroup gameControlGroup) {
		// Override �ؼ� ���
	}
	
	protected void onStart(GamePlatformInfo platformInfo) {
		// Override �ؼ� ���
	}
	
	protected void onTick(GamePlatformInfo platformInfo) {
		// Override �ؼ� ���
	}
	
	protected void onDraw(GamePlatformInfo platformInfo) {
		// Override �ؼ� ���
	}
	
	protected boolean onKeyDown(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		// Override �ؼ� ���
		return false;
    }

    protected boolean onKeyUp(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		// Override �ؼ� ���
		return false;
    }

    protected boolean onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
		// Override �ؼ� ���
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
