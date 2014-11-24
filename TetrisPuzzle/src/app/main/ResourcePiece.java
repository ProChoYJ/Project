package app.main;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import app.main.R;

public class ResourcePiece extends BitmapList {

	private static ResourcePiece _Instance = new ResourcePiece();

	public static ResourcePiece getInstance(){
		return _Instance;
	}
	
	private ResourcePiece() {}
	
    //public static final int WIDTH = 122;
   // public static final int HEIGHT = 65;

    @Override
    protected void loadBitmaps() {
		_Bitmaps = new Bitmap[2];
		
		Resources resources = Resource.getInstance().getResources();  
		
        _Bitmaps[0] = BitmapFactory.decodeResource(resources, R.drawable.background);
        _Bitmaps[1] = BitmapFactory.decodeResource(resources, R.drawable.down);
    }
	
}
