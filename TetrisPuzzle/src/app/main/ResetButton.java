package app.main;

import ryulib.OnNotifyEventListener;
import ryulib.game.ImageButton;

public class ResetButton{

	public static ImageButton Instance = null;	
	
	public static void createInstance() {
		
		Instance = new ImageButton(Platform.Instance.getGameControlGroup());
		Instance.setPosition(600, 300);
		Instance.setImageUp(R.drawable.up);
		Instance.setImageDown(R.drawable.down);
		Instance.setOnClick(				
				new OnNotifyEventListener() {
					@Override
					public void onNotify(Object sender) {
					
					}
			    }		
		);
	}
		
	private ResetButton() { }
	
}
