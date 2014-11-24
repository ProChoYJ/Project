package ryulib.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;

import ryulib.ValueList;

public class NotifyPacket implements Runnable {
	
	private ArrayList<Object> _Observers = null;
	private ValueList _Packet = null;
	
	public NotifyPacket(ArrayList<Object> AObservers, ValueList APacket) {
		_Observers = AObservers;
		_Packet = APacket;
	}
	
	private void do_Notify(Object AObject, ValueList APacket) {
		Class _Class = AObject.getClass();
		Class[] _ParameterType = new Class[] { ValueList.class };
		
		try {
			Method _Method = _Class.getMethod("rp_" + APacket.get("Code"), _ParameterType);
			_Method.invoke(AObject, new Object[] { APacket });
		} catch (Exception e) {
			// Todo : 
		}
	}
	
	public void run() {
		int i;
		for (i=0; i<_Observers.size(); i++) {
			do_Notify(_Observers.get(i), _Packet);
		}
	}
	
}
