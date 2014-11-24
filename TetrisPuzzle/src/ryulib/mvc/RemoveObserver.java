package ryulib.mvc;

import java.util.ArrayList;

public class RemoveObserver implements Runnable {

	private ArrayList<Object> _Observers = null;
	private Object _Object = null;

	public RemoveObserver(ArrayList<Object> AObservers, Object AObject) {
		_Observers = AObservers;
		_Object = AObject;
	}
	
	public void run() {
		_Observers.remove(_Object);
	}
	
}
