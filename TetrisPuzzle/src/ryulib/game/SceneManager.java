package ryulib.game;

import java.util.HashMap;

import ryulib.ValueList;

public class SceneManager {
	
	public SceneManager(GamePlatform gamePlatform) {
		super();
		
		_GamePlatform = gamePlatform;
	}
	
	private HashMap<String, IScene> _SceneList = new HashMap<String, IScene>();
	private String _ActiveName = "";
	private IScene _ActiveScene = null;
	
	// Property 
	private GamePlatform _GamePlatform = null;
	
	public synchronized void clear() {
		_SceneList.clear();
	}
	
	public synchronized void add(String name, IScene scene) {
		_SceneList.put(name, scene);
		scene.setSceneManager(this);
	}
	
	public synchronized void remove(String name) {
		_SceneList.remove(name);
	}
	
	public synchronized void setActiveName(String name, ValueList params) {
		IScene scene = _SceneList.get(name);
		// TODO : raise Exception
		if (scene == null) return;
		
		IScene temp = _ActiveScene;
		
		_ActiveName = name;
		_ActiveScene = scene;
		
		if (temp != null) temp.actionOut(scene);
		scene.actionIn(temp, params);
	}
	
	public String getActiveName() {
		return _ActiveName;
	}
	
	public IScene getActiveScene() {
		return _ActiveScene;
	}

	public GamePlatform getGamePlatform() {
		return _GamePlatform;
	}

}
