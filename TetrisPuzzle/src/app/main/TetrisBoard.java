package app.main;
// 여기서 전체 크기를 조정 해야함
import ryulib.OnNotifyEventListener;
import ryulib.game.GameControl;
import ryulib.game.GameControlGroup;
import ryulib.game.GamePlatformInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.Toast;

public class TetrisBoard extends GameControl {

	public TetrisBoard(GameControlGroup gameControlGroup,int game) {
		super(gameControlGroup);
		number = game;
		gameControlGroup.addControl(this);
		
        _PieceFactory = new PieceFactory(gameControlGroup);
       _PieceFactory.setOnNewPiece(_OnNewPiece);
        
        _PieceList.setOnGameEnd(_OnGameEnd);
	}
	
	private int number;
	private PieceFactory _PieceFactory = null;
	private PieceList _PieceList = new PieceList();
	// 리스너가 반응 할떄마다 실행
	private OnNotifyEventListener _OnNewPiece = new OnNotifyEventListener() {
		@Override
		public void onNotify(Object sender) {
			getGameControlGroup().addControl((Piece) sender);
			_PieceList.add((Piece) sender);
		}
	};
	//게임이 긑나게 되면 실행 하게될 메소드
	private OnNotifyEventListener _OnGameEnd = new OnNotifyEventListener() {
		@Override
		public void onNotify(Object sender) {
			
		//	System.exit(0);
			StartgameActivity.bld.show();
			
		}
	};
	
	//private Bitmap _BoardBitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
	private Bitmap _BoardBitmap = Global.bit[1];
	private Canvas _BoardCanvas = new Canvas();
	
	private Canvas _Canvas = null;
	public static Canvas _Score = null;
	private Canvas Timer = null;
	//public static GameTimer gt = new GameTimer();
	private Paint _Paint = new Paint();
	private Paint _Text = new Paint();
	// 게임이 시작 될 떄  // next 버튼을 만들어서 누르면 다시 게임 스타트가 됨  새로운 정보로 다시 시작(다음판)
	public void startGame() {
		_PieceList.clear();
		_PieceFactory.slice(number); // 전달 인자를 넘겨 줘서 실행하게  판다마 모두 
		
	}
	
	@Override
	// 스크린 싸이즈 ///////////// 좀더 뒤져보기
	protected void onStart(GamePlatformInfo platformInfo) {
		_Canvas = platformInfo.getCanvas();
		_Score = platformInfo.getCanvas();
		Timer = platformInfo.getCanvas();
        Global.setScreenSize(_Canvas.getWidth(), _Canvas.getHeight());
		//Global.setScreenSize(480,320);
		setBoardSize(Global.blockSize * Global.boardSize);
		// 게임이 시작 되면
		startGame();
		GameTimer.gt.start();
		
	}
	// 색
	@Override // 배경 지정
	protected void onDraw(GamePlatformInfo platformInfo) {
		
		_Paint.setARGB(255, 0, 0, 0);
		_Text.setARGB(255, 0, 0, 0);
		_Text.setTextSize(20);
		_Text.setAntiAlias(true);
		_Canvas.drawRect(platformInfo.getRect(), _Paint);
		
		
		_Canvas.drawBitmap(_BoardBitmap, null, new Rect(0,0,_Canvas.getWidth(),_Canvas.getHeight()), _Paint);	
		_Score.drawText("Moved : " + PieceList.Moved, (_Canvas.getWidth()*4)/5, (_Canvas.getHeight()*3)/10, _Text);
	//	Timer.drawText("Time : " + GameTimer.gt.Counttime, (_Canvas.getWidth()*4)/5, (_Canvas.getHeight()*4)/10, _Text);
		if(GameTimer.gt.Counttime != 0){
			Timer.drawText("Time : " + GameTimer.gt.Counttime, (_Canvas.getWidth()*8)/10, (_Canvas.getHeight()*4)/10, _Text);
		}
		else if(GameTimer.gt.Counttime == 0){
			Timer.drawText("얼어 죽음", (_Canvas.getWidth()*8)/10, (_Canvas.getHeight()*4)/10, _Text);	
			Timer.drawText("3초뒤 게임종료", (_Canvas.getWidth()*8)/10, (_Canvas.getHeight()*5)/10, _Text);	
		}
		
		
		// 게임 종료
		
		//_Canvas.drawBitmap(Global.bit[1], 0, 0, _Paint);
	}
	
	private int _BoardSize = 0;

	public int getBoardSize() {
		return _BoardSize;
	}

	public void setBoardSize(int _BoardSize) {
		this._BoardSize = _BoardSize;
		doPrepareBoardBitmap(_BoardSize);		
	}
	// 선을 그림 블락 크기 만큼
	private void doPrepareBoardBitmap(int boardSize) {
		//_BoardBitmap = Bitmap.createBitmap(boardSize+1, boardSize+1, Config.ARGB_8888);
		//_BoardBitmap = Global.bit[1];
		//_BoardCanvas.setBitmap(_BoardBitmap);		
//		
//		_Paint.setARGB(100, 255, 255, 255);
//
//		for (int x=0; x<=Global.boardSize; x++) {
//			_BoardCanvas.drawLine(x*Global.blockSize, 0, x*Global.blockSize, boardSize, _Paint);
//		}
//		
//		for (int y=0; y<=Global.boardSize; y++) {
//			_BoardCanvas.drawLine(0, y*Global.blockSize, boardSize, y*Global.blockSize, _Paint);
//		}
	}

}
