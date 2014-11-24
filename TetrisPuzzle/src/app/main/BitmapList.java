package app.main;

import android.graphics.Bitmap;

public abstract class BitmapList {

	protected Bitmap[] _Bitmaps = null;

	public Bitmap getBitmap(int index) {
    	if (_Bitmaps == null) loadBitmaps();
    	return _Bitmaps[index];
    }
    
    public int getCount() {
    	if (_Bitmaps == null) loadBitmaps();
    	return _Bitmaps.length;
    }
    
    abstract protected void loadBitmaps();
    
}
