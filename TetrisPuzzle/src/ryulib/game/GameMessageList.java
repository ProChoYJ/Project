package ryulib.game;

import java.util.ArrayList;

public class GameMessageList {
	
	private ArrayList<GameMessage> _List = new ArrayList<GameMessage>();
	
	public synchronized void clear() {
		_List.clear();
	}

	public synchronized void add(GameMessage gameMessage) {
		_List.add(gameMessage);
	}

	public synchronized void remove(GameMessage gameMessage) {
		_List.remove(gameMessage);
	}
	
	private GameMessage _NullMessage = new GameMessage();
	
	public synchronized GameMessage get() {
		if (_List.size() == 0) return _NullMessage;
		
		GameMessage gameMessage = _List.get(0);
		_List.remove(0);
		
		return gameMessage;
	}
	
	public synchronized int size() {
		return _List.size();
	}

}
