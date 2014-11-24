package ryulib.game;

import ryulib.OnNotifyEventListener;

public class GameControl extends GameControlBase {
	
	public GameControl(GameControlGroup gameControlGroup) {
		super(gameControlGroup);	
		
	}
	
	public GameControl checkCollision(GameControl target) {
		if (_GameControlGroup != null) {
			return _GameControlGroup.checkCollision(target);
		} else {
			return null;
		}		
	}
	
	public void checkCollision(GameControl target, OnNotifyEventListener event) {
		if (_GameControlGroup != null) 
			_GameControlGroup.checkCollision(target, event);
	}
	
}
