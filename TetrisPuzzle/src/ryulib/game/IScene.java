package ryulib.game;

import ryulib.ValueList;

public interface IScene {

	public void setSceneManager(SceneManager sceneManager);
	public void actionIn(IScene AOldScene, ValueList AParams);
	public void actionOut(IScene ANewScene);	

}
