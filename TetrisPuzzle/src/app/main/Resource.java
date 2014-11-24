package app.main;

import android.content.Context;
import android.content.res.Resources;

public class Resource {

	private static Resource _Instance = new Resource();

	public static Resource getInstance(){
		return _Instance;
	}
	
	private Resource() {}
	  
	private Context _Context = null;
	
	public Resources getResources() {
		if (_Context == null) return null;
		return _Context.getResources();
	}
	
	public void setContext(Context value) {
		_Context = value;
	}

	public Context getContext() {
		return _Context;
	}	
	
}
