package ryulib.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;

import ryulib.ValueList;

import android.os.Handler;

public class ObserverList {

	public void clear() {
		_List.clear();
	}
	
	public void add(Object AObject) {
		_List.add(AObject);
	}
	
	public void remove(Object AObject) {
		// ���� ���� ���� Observer�� �������� �ʵ��� ť�� �׾Ƽ� �����Ѵ�.
		RemoveObserver _RemoveObserver = new RemoveObserver(_List, AObject);
		_Handler.post(_RemoveObserver);
	}
	
	public void asyncBroadcast(ValueList APacket) {
		NotifyPacket _NotifyPacket = new NotifyPacket(_List, APacket);
		_Handler.post(_NotifyPacket);
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

	public void broadcast(ValueList APacket) {
		int i;
		for (i=0; i<_List.size(); i++) {
			do_Notify(_List.get(i), APacket);
		}
	}
	
	private ArrayList<Object> _List = new ArrayList<Object>(); 

	private Handler _Handler = new Handler();

}
