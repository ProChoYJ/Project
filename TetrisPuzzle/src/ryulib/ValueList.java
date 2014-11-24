package ryulib;

import java.util.HashMap;

public class ValueList extends HashMap<String, String> {

	private static final long serialVersionUID = 1065478499595139080L;
	
	public void put(String AKey, int AValue) {
		put(AKey, Integer.toString(AValue));
	}
	
	public int getInt(String AKey) {
		return Integer.parseInt(get(AKey));
	}
}
