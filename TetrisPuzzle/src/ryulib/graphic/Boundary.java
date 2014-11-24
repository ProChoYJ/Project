package ryulib.graphic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Boundary {
	
	public Boundary(int Left, int Top, int Right, int Bottom) {
		setBoundary(Left, Top, Right, Bottom);
	}
	
	public Boundary(Boundary boundary) {
		setBoundary(boundary);
	}
	
	private int _Left;
	private int _Top;
	private int _Right;
	private int _Bottom;
	private Rect _Rect = new Rect();	
	
	// 직선 (S1, E1), (S2, E2)가 서로 교차하는 지 검사한다.
	private boolean check_LineCollision(int S1, int E1, int S2, int E2) {
		boolean A = (S1 <= S2) && (S2 <= E1);
		boolean B = (S1 <= E2) && (E2 <= E1);
		boolean C = (S2 <= S1) && (S1 <= E2);
		boolean D = (S2 <= E1) && (E1 <= E2);
		
		return (A || B || C || D);			
	}
	
	public boolean CheckCollision(Boundary ADest) {
		return 
			check_LineCollision(_Left, _Right, ADest.getLeft(), ADest.getRight()) && 
			check_LineCollision(_Top, _Bottom, ADest.getTop(), ADest.getBottom());
	}
	
	public void setLeft(int ALeft) {
		int Temp = _Left;
		
		_Left = ALeft;
		_Right = _Right + _Left - Temp;
	}
	
	public void incLeft(int value) {
		_Left += value;
		_Right = _Right + value;
	}
	
	public void setTop(int ATop) {
		int Temp = _Top;
		
		_Top = ATop;
		_Bottom = _Bottom + _Top - Temp;
	}
	
	public void incTop(int value) {
		_Top += value;
		_Bottom = _Bottom + value;
	}
	
	public void setRight(int ARight) {
		int Temp = _Right;
		
		_Right = ARight;
		_Left = _Left + _Right - Temp;
	}
	
	public void setBottom(int ABottom) {
		int Temp = _Bottom;
		
		_Bottom = ABottom;
		_Top = _Top + _Bottom - Temp;
	}
	
	public void setBoundary(int ALeft, int ATop, int ARight, int ABottom) {
		
		_Left   = ALeft;
		_Top    = ATop;
		_Right  = ARight;
		_Bottom = ABottom;
	}
	
	public void setBoundary(Boundary value) {
		_Left   = value._Left;
		_Top    = value._Top;
		_Right  = value._Right;
		_Bottom = value._Bottom;
	
	}
	
	public int getLeft() {
		return _Left;
	}

	public int getTop() {
		return _Top;
	}

	public int getRight() {
		return _Right;
	}

	public int getBottom() {
		return _Bottom;
	}
	
	public int getWidth() {
		return _Right - _Left;
	}

	public int getHeight() {
		return _Bottom - _Top;
	}
	
	public Rect getRect() {
		_Rect.set(_Left, _Top, _Right, _Bottom);
		return _Rect;
	}
	
	public Rect getRect(int x, int y) {
		_Rect.set(_Left+x, _Top+y, _Right+x, _Bottom+y);
		return _Rect;
	}
	
	public Rect getRect(Point point) {
		return getRect(point.x, point.y);
	}
	
	public int getHorizontalCenter() {
		return _Left + (getWidth() / 2);
	}

	public int getVerticalCenter() {
		return _Top + (getHeight() / 2);
	}

	public void setHorizontalCenter(int value) {
		setLeft(value - (getWidth() / 2));
	}

	public void setVerticalCenter(int value) {
		setTop(value - (getHeight() / 2));
	}

	public void setCenter(int x, int y) {
		setHorizontalCenter(x);	
		setVerticalCenter(y);
	}
	
	public boolean isMyArea(int x, int y) {
		return (x >= _Left) && (x <= _Right) && (y >= _Top) && (y <= _Bottom);
	}
	
	public void drawTextCenter(Canvas canvas, Paint paint, String text) {
		paint.setTextAlign(Paint.Align.CENTER);

		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);
		
		canvas.drawText(text, getHorizontalCenter(), getVerticalCenter() + ((rect.bottom-rect.top) / 2), paint);
	}

}
