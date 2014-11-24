package app.main;

import ryulib.graphic.Boundary;
import android.graphics.Point;

public class Block {
	
	public Block(int x, int y) {
		super();
		
		_Point.set(x, y);
		updateBoundary();
	}
	
	private Point _Point = new Point();
	private Boundary _Boundary = new Boundary(1, 1, Global.blockSize-2, Global.blockSize-2);
	
	public Point getPoint() {
		return _Point;
	}
	
	public int getX() {
		return _Point.x;
	}
	
	public int getY() {
		return _Point.y;
	}
	
	private void updateBoundary() {
		_Boundary.setBoundary(
			(_Point.x * Global.blockSize) + 1,
			(_Point.y * Global.blockSize) + 1,
			((_Point.x+1) * Global.blockSize) - 2,
			((_Point.y+1) * Global.blockSize) - 2
		);
	
	}

	public void decX(int value) {
		_Point.x = _Point.x - value;
		updateBoundary();
	}

	public void decY(int value) {
		_Point.y = _Point.y - value;
		updateBoundary();
	}

	public Boundary getBoundary() {
		return _Boundary;
	}

	private Boundary _BoundaryCopy = new Boundary(_Boundary);
	
	public Boundary getBoundary(int x, int y) {
		_BoundaryCopy.setBoundary(_Boundary);
		_BoundaryCopy.incLeft(x * Global.blockSize);
		_BoundaryCopy.incTop (y * Global.blockSize);
		
		return _BoundaryCopy;
	}

}
