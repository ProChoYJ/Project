package ryulib.game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class JoyStick extends JoyStickInterface implements SensorEventListener {

	public JoyStick() {
		super();
		
	}

	public JoyStick(int speed) {
		super(speed);
		
	}
	
	public void PrepareOrientationSensor(Context context) {
	    SensorManager _SensorManager;
	    Sensor _Sensor;    
	    
		_SensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		_Sensor = _SensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
        if (_Sensor != null) {
        	_SensorManager.registerListener(this, _Sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

	private int _TargetY = 0;
	private boolean _IsMoving = false;
	
	// 기울여진 상태가 아니라고 판단하게 되는 각도, 
	// 즉, 아래에서 설정된 값으로 기울여진 상태는 평평하다고 간주한다.
	private int _DefaultAngle = 0;
	
	// 얼마나 기울여야 반응 할 것 인지.  이 이상 기울여야지만 반응하게 된다.
	private int _AngleLimit = 5;

	
	@Override
	public void tick(long tick) {
		super.tick(tick);
		
		if (_IsMoving) {
			if ((_DY < 0) && (_Y <= _TargetY)) {
				_IsMoving = false;
				_DY = 0;
				_Y = _TargetY;
			}
			
			if ((_DY > 0) && (_Y >= _TargetY)) {
				_IsMoving = false;
				_DY = 0;
				_Y = _TargetY;
			}
		}
	}
	
	public void onKeyDown(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT: _DX = -1; break;
	
			case KeyEvent.KEYCODE_DPAD_RIGHT: _DX = +1; break;
	
			case KeyEvent.KEYCODE_DPAD_UP: 
			case KeyEvent.KEYCODE_Q: _DY = -1; break;
	
			case KeyEvent.KEYCODE_DPAD_DOWN:
			case KeyEvent.KEYCODE_W: _DY = +1; break;
		}
    }

	public void onKeyUp(GamePlatformInfo platformInfo, int keyCode, KeyEvent msg) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT: if (_DX == -1) _DX =0; break;
		
			case KeyEvent.KEYCODE_DPAD_RIGHT: if (_DX == +1) _DX =0; break;
	
			case KeyEvent.KEYCODE_DPAD_UP: 
			case KeyEvent.KEYCODE_Q: if (_DY == -1) _DY =0; break;
			
			case KeyEvent.KEYCODE_DPAD_DOWN:
			case KeyEvent.KEYCODE_W: if (_DY == +1) _DY =0; break;
		}
    }

	public void onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
		_TargetY = (int) event.getY();
		_IsMoving = true;
		
		if (_TargetY > _Y) _DY = 1;
		else if (_TargetY < _Y) _DY = -1;
		else _IsMoving = false;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		int x = (int) event.values[SensorManager.DATA_Y];
		int y = (int) event.values[SensorManager.DATA_Z];
		
		if (x < (- _AngleLimit)) _DX = 1;
		else if (x > (_AngleLimit)) _DX = -1;
		else _DX = 0;
		
		if (y < (_DefaultAngle - _AngleLimit)) _DY = -1;
		else if (y > (_DefaultAngle + _AngleLimit)) _DY = 1;
		else _DY = 0;
	}

	public void setDefaultAngle(int _DefaultAngle) {
		this._DefaultAngle = _DefaultAngle;
	}

	public int getDefaultAngle() {
		return _DefaultAngle;
	}

	public void setAngleLimit(int _AngleLimit) {
		this._AngleLimit = _AngleLimit;
	}

	public int getAngleLimit() {
		return _AngleLimit;
	}
	
}
