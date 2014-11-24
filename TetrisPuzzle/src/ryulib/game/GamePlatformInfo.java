package ryulib.game;

import ryulib.OnHandlerMessageListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;

public class GamePlatformInfo {

	public GamePlatformInfo(GamePlatform gamePlatform) {
		super();
		
		_GamePlatform = gamePlatform;
		_Paint.setAntiAlias(true);
	}

	private GamePlatform _GamePlatform = null;
	private Bitmap _Bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
	private Canvas _Canvas = new Canvas();
	private Paint _Paint = new Paint();
	private long _Tick = 0;
	
	private GameMessageList _GameMessageList = new GameMessageList();
	private GameMessage _GameMessage = null;

	private Handler _Handler = new Handler() {		
		public void handleMessage(Message msg) {
			if (_OnHandlerMessage != null) _OnHandlerMessage.onReceived(msg);
		}
	};
	
	final void setTick(long value) {
		// Tick 간격이 너무 크다면 에러이거나 병목 등의 문제일 수 있다.  
		// 이때, 생각 이외의 동작을 유발 할 수 있으므로 Term의 크기를 제한한다.
		if (value > _TickLimit) value = _TickLimit;

		_Tick = value;
	}

	final void getNextMessage() {
		_GameMessage = _GameMessageList.get();
	}
	
	final synchronized void setSize(int width, int height) {
		  _Bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		  _Canvas.setBitmap(_Bitmap);
	}
	
	final synchronized void draw(Canvas canvas) {
		canvas.drawBitmap(_Bitmap, 0, 0, _Paint);
	}
	
	private static final int _TickLimit = 500;

	private OnHandlerMessageListener _OnHandlerMessage = null;
	
	void setOnHandlerMessage(OnHandlerMessageListener value) {
		_OnHandlerMessage = value;
	}

	public final void addMessage(GameMessage gameMessage) {
		_GameMessageList.add(gameMessage);
	}
	
	public final void addMessage(Object sender, int what) {
		GameMessage gameMessage = new GameMessage();
		gameMessage.sender = sender;
		gameMessage.what = what;
		
		_GameMessageList.add(gameMessage);
	}
	
	public final void addMessage(Object sender, String str) {
		GameMessage gameMessage = new GameMessage();
		gameMessage.sender = sender;
		gameMessage.str = str;
		
		_GameMessageList.add(gameMessage);
	}
	
	public final Bitmap getBitmap() {
		return _Bitmap;
	}

	public final Canvas getCanvas() {
		return _Canvas;
	}
	
	public final Paint getPaint() {
		return _Paint;
	}
	
	public final GameMessage getGameMessage() {
		return _GameMessage;
	}
	
	public final Handler getHandler() {
		return _Handler;
	}
	
	public final long getTick() {
		return _Tick;
	}
	
	private Rect _Rect = new Rect();
	
	public final Rect getRect() {
		_Rect.left = 0;
		_Rect.top = 0;
		_Rect.right = _Canvas.getWidth();
		_Rect.bottom = _Canvas.getHeight();
		
		return _Rect;
	}

	public final GamePlatform getGamePlatform() {
		return _GamePlatform;
	}
	
}
