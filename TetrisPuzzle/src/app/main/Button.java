package app.main;

import ryulib.OnNotifyEventListener;
import ryulib.game.ImageButton;

public class Button{

	public static ImageButton Instance = null;	
	public static ImageButton Instance2 = null;
	public static void createInstance() {
		Instance = new ImageButton(Platform.Instance.getGameControlGroup());
		Instance.setPosition(600, 10);
		Instance.setImageUp(R.drawable.up);
		Instance.setImageDown(R.drawable.down);
		Instance.setOnClick(				
				new OnNotifyEventListener() {
					@Override
					public void onNotify(Object sender) {
						
					}
			    }		
		);
		
		Instance2 = new ImageButton(Platform.Instance.getGameControlGroup());
		Instance2.setPosition(600, 300);
		Instance2.setImageUp(R.drawable.up);
		Instance2.setImageDown(R.drawable.down);
		Instance2.setOnClick(				
				new OnNotifyEventListener() {
					@Override
					public void onNotify(Object sender) {
					
					}
			    }		
		);
	}
		
	private Button() { }
	
}
