package ryulib.internet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {
	
	static public int ConnectTimeout = 5 * 1000;
	
	static public String get(String AAddress) {
		StringBuilder _Result = new StringBuilder();

		try {
			URL _URL = new URL(AAddress);
			
			HttpURLConnection _Connection = (HttpURLConnection) _URL.openConnection();
			if (_Connection != null) {
				_Connection.setConnectTimeout(ConnectTimeout);
				_Connection.setUseCaches(false);
				if (_Connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader _Reader = 
						new BufferedReader(new InputStreamReader(_Connection.getInputStream()));
					while (true) {
						String _Line = _Reader.readLine();
						if (_Line == null) break;
						
						_Result.append(_Line + '\n');
					}
					_Reader.close();
				} else {
					throw new Exception(String.format("ErrorCode = %d", _Connection.getResponseCode()));
				}
					
				_Connection.disconnect();
			}
		} catch (Exception E) {
		}
		
		return _Result.toString();
	}

}
