package ryulib;

import android.util.Log;

public class ThreadRepeater implements Runnable {
	
	private Boolean _Active = false; 
	private Boolean _Pause = false; 
	private Thread _Thread = null;
	
	// Property
	private long _Interval = 0;
	
	// Event
	private OnNotifyEventListener _OnNotifyEvent = null;

	public final void start() {
		_Active = true;
		_Thread = new Thread(this);
		_Thread.start();
	}
	
	public final void pause() {
		_Pause = true;
	}
	
	public final void resume() {
		_Pause = false;
	}
	
	public final void stop() {
        _Active = false;
        if (_Thread == null) return;

        boolean retry = true;
        while (retry) {
            try {
            	_Thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}

	public final void run() {
		while (_Active) {
			if ((_Pause == false) && (_OnNotifyEvent != null)) {
				_OnNotifyEvent.onNotify(this);
				
        		if (_Interval > 0)  {
        			try {
		        		Thread.sleep(_Interval);
					} catch (InterruptedException e) {
						Log.w("GamePlatform Class", "Thread.sleep Exception.");
					}       		
        		}
			}				
		}		
	}
	
	public final void setOnNotifyEvent(OnNotifyEventListener value) {
		_OnNotifyEvent = value;
	}

	public final void setInterval(long value) {
		_Interval = value;
	}

	public final long getInterval() {
		return _Interval;
	}

}
