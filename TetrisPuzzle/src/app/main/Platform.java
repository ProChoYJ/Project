package app.main;

import ryulib.game.GamePlatform;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Platform {

	public static GamePlatform Instance = null;	
	
	public static void createInstance(Activity context) {
        Instance = new GamePlatform(context);
        Instance.setUseMotionEvent(true);
        Instance.setLayoutParams(
        		new LinearLayout.LayoutParams(
        				ViewGroup.LayoutParams.WRAP_CONTENT,
        				ViewGroup.LayoutParams.WRAP_CONTENT,
        				
        				0.0F)//0.0F, ViewGroup.LayoutParams.FILL_PARENT
        );
        
        context.setContentView(Instance);
	}
	private Platform() { }
	
}
