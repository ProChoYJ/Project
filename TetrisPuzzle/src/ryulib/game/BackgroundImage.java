package ryulib.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BackgroundImage {

	private int _X01 = 0;
	private int _X02 = 0;
	private Scroll _Scroll = new Scroll();

	// Property 
	private Bitmap _Bitmap = null;
	
	public void draw(Canvas canvas, Paint paint, long tick) {
		// 현재 위치에 먼 거리 배경 이미지 그리기
		if ((_Bitmap.getWidth()+_X01) > 0) {
			canvas.drawBitmap(_Bitmap, _X01, 0, paint);
		}
		
		// 첫 번째 이미지가 화면을 꽉 채우지 못할 경우, 나머지 공간에 두 번째 이미지를 그린다.
		if ((_Bitmap.getWidth()+_X01) <= canvas.getWidth()) {
			canvas.drawBitmap(_Bitmap, _X02, 0, paint);
		}
		
		// 초당 _Scroll.getSpeed() 만큼의 속도로 운석을 이동 한다.
		_X01 = _X01 - _Scroll.move(tick);
		
		// 뒤에 따라와야하는 이미지의 좌표
		_X02 = _X01 + _Bitmap.getWidth();
		
		// 두 번째 이미지가 화면을 꽉 채우지 못하면, 지나간 첫 번째 이미지를 재 활용한다.
		if (_X02+(_Bitmap.getWidth()) <= canvas.getWidth()) {
			int temp = _X01;
			_X01 = _X02;
			_X02 = temp + _Bitmap.getWidth();
		}
	}
	
	public void setBitmap(Bitmap value) {
		_Bitmap = value;
	}
	public Bitmap getBitmap() {
		return _Bitmap;
	}

	public void setSpeed(int value) {
		_Scroll.setSpeed(value);
	}

	public int getSpeed() {
		return _Scroll.getSpeed();
	}

}
