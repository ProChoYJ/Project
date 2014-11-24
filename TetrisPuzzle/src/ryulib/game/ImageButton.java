package ryulib.game;

import ryulib.OnNotifyEventListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class ImageButton extends GameControl {

	public ImageButton(GameControlGroup gameControlGroup) {
		super(gameControlGroup);
		
		gameControlGroup.addControl(this);
	}
	
	private boolean _IsDowned = false;
	
	private Bitmap _BitmapUp = null; 
	private Bitmap _BitmapDown = null; 
	
	// Property
	private Point _Position = new Point(0, 0);
	private int _ImageUp = -1;
	private int _ImageDown = -1;
	private OnNotifyEventListener _OnClick = null; 
	
	@Override
	protected void onDraw(GamePlatformInfo platformInfo) {
		Bitmap bitmap = null;
		Resources resources =
			platformInfo.getGamePlatform().getContext().getResources();
		
		if (_IsDowned) {
			if (_BitmapDown == null) _BitmapDown = loadBitmap(resources, _ImageDown);			
			bitmap = _BitmapDown;
		} else {
			if (_BitmapUp == null) _BitmapUp = loadBitmap(resources, _ImageUp);			
			bitmap = _BitmapUp;
		}
		
		if (bitmap != null) {
			Paint paint = platformInfo.getPaint();
			Canvas canvas = platformInfo.getCanvas();
			
			canvas.drawBitmap(bitmap, _Position.x, _Position.y, paint);
		}
	}
	
	@Override
    protected final boolean onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		boolean isMyArea = getBoundary().isMyArea(x, y); 
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: doActionDown(isMyArea); break;
			case MotionEvent.ACTION_UP: doActionUp(isMyArea); break;
		}

		return isMyArea;
    }
	
	private Bitmap loadBitmap(Resources resources, int id) {
		Bitmap bitmap = null;
		
		if (id > -1) {
			bitmap = BitmapFactory.decodeResource(resources, id);
			getBoundary().setBoundary(
					_Position.x, 
					_Position.y, 
					_Position.x+bitmap.getWidth(), 
					_Position.y+bitmap.getHeight()
			);
		}
		
		return bitmap;
	}

	private void doActionDown(boolean isMyArea) {
		_IsDowned = isMyArea; 
	}
    
	private void doActionUp(boolean isMyArea) {
		if (isMyArea && _IsDowned) {
			if (_OnClick != null) 
				_OnClick.onNotify(this);
		}
		
		_IsDowned = false; 		
	}
    
	public final void setImageUp(int value) {
		_ImageUp = value;
		_BitmapUp = null;
	}
	
	public final int getImageUp() {
		return _ImageUp;
	}

	public final void setImageDown(int value) {
		_ImageDown = value;
		_BitmapDown = null;
	}

	public final int getImageDown() {
		return _ImageDown;
	}

	public void setOnClick(OnNotifyEventListener value) {
		_OnClick = value;
	}

	public OnNotifyEventListener getOnClick() {
		return _OnClick;
	}
	
	private void changeBoundary() {
		getBoundary().setLeft(_Position.x);
		getBoundary().setTop(_Position.y);
	}
	
	public void setPosition(int x, int y) {
		_Position.x = x;
		_Position.y = y;
		changeBoundary();
	}

	public void setX(int value) {
		_Position.x = value;
		changeBoundary();
	}

	public int getX() {
		return _Position.x;
	}

	public void setY(int value) {
		_Position.y = value;
		changeBoundary();
	}

	public int getY() {
		return _Position.y;
	}

}
