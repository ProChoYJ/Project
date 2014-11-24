package ryulib.graphic;

import android.graphics.Bitmap;

public abstract class BitmapList {

	protected Bitmap[] _Bitmaps = null;

	public Bitmap getBitmap(int index) {
    	if (_Bitmaps == null) loadBitmaps();
    	
    	if ((index >= 0) && (index < _Bitmaps.length)) {
    		return _Bitmaps[index];
    	} else {
    		return null;
    	}
    }
    
    public int getCount() {
    	if (_Bitmaps == null) loadBitmaps();
    	return _Bitmaps.length;
    }
    
    abstract protected void loadBitmaps();
    
}
