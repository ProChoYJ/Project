package app.main;
// ���⼭ ��ü ũ�⸦ ���� �ؾ���
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
	// �����ʰ� ���� �ҋ����� ����
	private OnNotifyEventListener _OnNewPiece = new OnNotifyEventListener() {
		@Override
		public void onNotify(Object sender) {
			getGameControlGroup().addControl((Piece) sender);
			_PieceList.add((Piece) sender);
		}
	};
	//������ �P���� �Ǹ� ���� �ϰԵ� �޼ҵ�
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
	// ������ ���� �� ��  // next ��ư�� ���� ������ �ٽ� ���� ��ŸƮ�� ��  ���ο� ������ �ٽ� ����(������)
	public void startGame() {
		_PieceList.clear();
		_PieceFactory.slice(number); // ���� ���ڸ� �Ѱ� �༭ �����ϰ�  �Ǵٸ� ��� 
		
	}
	
	@Override
	// ��ũ�� ������ ///////////// ���� ��������
	protected void onStart(GamePlatformInfo platformInfo) {
		_Canvas = platformInfo.getCanvas();
		_Score = platformInfo.getCanvas();
		Timer = platformInfo.getCanvas();
        Global.setScreenSize(_Canvas.getWidth(), _Canvas.getHeight());
		//Global.setScreenSize(480,320);
		setBoardSize(Global.blockSize * Global.boardSize);
		// ������ ���� �Ǹ�
		startGame();
		GameTimer.gt.start();
		
	}
	// ��
	@Override // ��� ����
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
			Timer.drawText("��� ����", (_Canvas.getWidth()*8)/10, (_Canvas.getHeight()*4)/10, _Text);	
			Timer.drawText("3�ʵ� ��������", (_Canvas.getWidth()*8)/10, (_Canvas.getHeight()*5)/10, _Text);	
		}
		
		
		// ���� ����
		
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
	// ���� �׸� ��� ũ�� ��ŭ
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
