package ryulib.game;

import ryulib.OnHandlerMessageListener;
import ryulib.OnNotifyEventListener;
import ryulib.ThreadRepeater;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePlatform extends SurfaceView implements SurfaceHolder.Callback {

	public GamePlatform(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init();
	}

	public GamePlatform(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	public GamePlatform(Context context) {
		super(context);

		init();
	}

	private void init() {
	    _SurfaceHolder.addCallback(this);
		_Repeater.setOnNotifyEvent(_OnRepeat);
		
	    // 키보드 이벤트를 받기 위해서는 포커스를 확보
	    setFocusable(true);
	}	

	private GameControlGroup _GameControlGroup = new GameControlGroup(null);
	private GamePlatformInfo _GamePlatformInfo = new GamePlatformInfo(this);
    private SurfaceHolder _SurfaceHolder = getHolder();
	private ThreadRepeater _Repeater = new ThreadRepeater();

	// Property
	private boolean _UseKeyEvent = false;
	private boolean _UseMotionEvent = false;
	private boolean _AutoInvalidate = false;  

	private void start() {
		_OldTick = System.currentTimeMillis();
		_Repeater.start();
	}
	
	private void stop() {
		_Repeater.stop();
	}
	
	public final void clearControls() {
		_GameControlGroup.clearControls();
	}
	
	public final void addControl(GameControlBase control) {
		_GameControlGroup.addControl(control);
	}

	public final void removeControl(GameControlBase control) {
		_GameControlGroup.removeControl(control);
	}
	
	@Override
	public final void surfaceCreated(SurfaceHolder holder) {
		start();
	}

	@Override
	public final void surfaceDestroyed(SurfaceHolder holder) {
		stop();
	}
	// 화면 크기 조정
	@Override
    public final void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	_GamePlatformInfo.setSize(width, height);
    	//_GamePlatformInfo.setSize(480, 320);
    	// TODO : GameControlBase에 OnSurfaceChanged 추가
	}

	@Override
    public final boolean onKeyDown(int keyCode, KeyEvent msg) {
    	if (_UseKeyEvent) {
    		_GameControlGroup.onKeyDown(_GamePlatformInfo, keyCode, msg);
    		if (_AutoInvalidate) _OnRepeat.onNotify(_Repeater);
    	}
        return true;
    }

	@Override
    public final boolean onKeyUp(int keyCode, KeyEvent msg) {
    	if (_UseKeyEvent) {
    		_GameControlGroup.onKeyUp(_GamePlatformInfo, keyCode, msg);
    		if (_AutoInvalidate) _OnRepeat.onNotify(_Repeater);
    	}
        return true;
    }

	@Override
	public final boolean onTouchEvent(MotionEvent event) {
		if (_UseMotionEvent) {
			_GameControlGroup.onTouchEvent(_GamePlatformInfo, event);
    		if (_AutoInvalidate) _OnRepeat.onNotify(_Repeater);
		}
		return true;
	}
	
	private long _OldTick = System.currentTimeMillis();

    private OnNotifyEventListener _OnRepeat = new OnNotifyEventListener() {
    	@Override
		public synchronized void onNotify(Object sender) {
			Canvas canvas = _SurfaceHolder.lockCanvas(null);
            try {
                if (canvas == null) return;
	                
				long tick = System.currentTimeMillis();
				_GamePlatformInfo.setTick(tick - _OldTick);
				_GamePlatformInfo.getNextMessage();

				_GameControlGroup.onRepeate(_GamePlatformInfo);

				_OldTick = tick;

        		_GamePlatformInfo.draw(canvas);
            } finally {
                if (canvas != null) {
                	_SurfaceHolder.unlockCanvasAndPost(canvas);
                }
	    	}
            
            _GameControlGroup.arrangeControls();
		}
	};
	
	public final GameControlGroup getGameControlGroup() {
		return _GameControlGroup;
	}
	
	public final GamePlatformInfo getGamePlatformInfo() {
		return _GamePlatformInfo;
	}

	public final void setOnHandlerMessage(OnHandlerMessageListener value) {
		_GamePlatformInfo.setOnHandlerMessage(value);
	}

	public final void setUseKeyEvent(boolean _UseKeyEvent) {
		this._UseKeyEvent = _UseKeyEvent;
	}

	public final boolean getUseKeyEvent() {
		return _UseKeyEvent;
	}

	public final void setUseMotionEvent(boolean _UseMotionEvent) {
		this._UseMotionEvent = _UseMotionEvent;
	}

	public final boolean getUseMotionEvent() {
		return _UseMotionEvent;
	}

	public final void setRepeatInterval(long value) {
		_Repeater.setInterval(value);
	}

	public final long getRepeatInterval() {
		return _Repeater.getInterval();
	}

	public final void setAutoInvalidate(boolean value) {
		// CPU 효율을 위해서, ThreadRepeater를 느리게 하고, 대신 키보드 메시지 등의 처리 이후 화면 갱신을 한다.
		_AutoInvalidate = value;
	}

	public final boolean isAutoInvalidate() {
		return _AutoInvalidate;
	}

}
