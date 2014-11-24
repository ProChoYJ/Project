package app.main;

import java.util.ArrayList;

import ryulib.OnNotifyEventListener;
import ryulib.game.GameControl;
import ryulib.game.GameControlGroup;
import ryulib.graphic.Boundary;
import android.graphics.Point;

public class PieceFactory extends GameControl {
	
	public PieceFactory(GameControlGroup gameControlGroup) {
		super(gameControlGroup);
		
	}

	private Boundary[][] _Boundaries = null;
	private int _BoundriesCount;
	
	public void slice(int game) {
		createCells(); // ���� �ϳ��� ����� (��� �µθ�)
		makePieces(game); // ������� ������ ���´� 2������ �°�
	}
	// if 2 �̰� 1 �̸� 2��¥���� ���η� ������ �̷������� ������ �����ؼ� �����
	// ������� ��ġ, ����, ���μ���
	private void makePieces(int game) { // ���� ���� : Bcount, Psize, _X, _Y
			// ����� �ϳ� ���� ������ �ޱ�
			// 2��¥�� 3��¥�� makepice �� ����� �ű⿡ _BoundriesCount ����� �°� �ϸ� ������ٰ� 
			// ��ġ�ϴ� �ڵ� �����
			// _BoundriesCount �� ��� ����, �̰ɰ����� �������� ���� �����ϰ�
		//_BoundriesCount = Global.boardSize * Global.boardSize;
	//	_BoundriesCount = 6;
	//	while (_BoundriesCount > 0) {
		//for���� �Ἥ 3���� 3��¥��  ��ŭ 2��¥�� ��ŭ �ƴϸ� switch case�� �̿� 1ź�̸� ~~~ 2ź�̸� ~~~
		if(game == 1){
 			makePiece(3,5,0,2,0); // ���� 3��¥�� 
			makePiece(2,4,4,1,0); // ���� 3��¥��
			makePiece(2,3,4,2,0); // ���� 3��¥��
			makePiece(2,3,2,1,1); // key
			GameTimer.Counttime=100;
		}
		if(game == 2){
			makePiece(2,2,2,1,1); makePiece(3,4,0,2,0); makePiece(3,3,3,2,0); makePiece(3,0,4,1,0); makePiece(2,4,5,1,0);
			GameTimer.Counttime=100;
		}
		if(game == 3){
			makePiece(2,3,2,1,1); makePiece(3,0,0,2,0); makePiece(3,1,2,2,0); makePiece(2,1,1,1,0); makePiece(2,2,2,2,0);
			makePiece(2,2,4,2,0); makePiece(2,3,0,2,0); makePiece(3,3,3,1,0); makePiece(3,3,4,1,0); makePiece(2,5,1,2,0);
			makePiece(2,4,0,1,0); GameTimer.Counttime=120;
		}
		if(game == 4){
			makePiece(2,1,2,1,1); makePiece(3,3,0,1,0); makePiece(2,2,0,2,0); makePiece(3,3,1,2,0); makePiece(2,1,3,2,0);
			makePiece(2,2,4,1,0); makePiece(2,2,5,1,0); makePiece(2,4,4,2,0); makePiece(2,5,4,2,0); GameTimer.Counttime=150;
		}
		if(game == 5){
			makePiece(2,1,2,1,1); makePiece(3,0,2,2,0); makePiece(3,0,5,1,0); makePiece(3,3,3,2,0); makePiece(2,1,3,1,0);
			makePiece(2,2,0,2,0); makePiece(2,3,0,1,0); makePiece(2,5,0,2,0); makePiece(2,3,1,2,0); makePiece(2,5,2,2,0);
			makePiece(2,4,2,2,0); GameTimer.Counttime=150;
		}
		if(game == 6){
			makePiece(2,0,2,1,1); makePiece(2,0,0,2,0); makePiece(2,1,0,1,0); makePiece(2,2,1,2,0); makePiece(2,0,3,1,0);
			makePiece(3,0,4,1,0); makePiece(2,3,4,2,0); makePiece(2,4,0,2,0); makePiece(3,4,2,2,0); makePiece(2,5,1,2,0);
			GameTimer.Counttime=180;
		}
		if(game == 7){
			makePiece(2,0,2,1,1); makePiece(2,0,0,2,0); makePiece(2,1,0,2,0); makePiece(2,2,0,1,0); makePiece(3,4,0,2,0);
			makePiece(2,5,0,2,0); makePiece(2,5,2,2,0); makePiece(2,3,1,2,0); makePiece(2,2,2,2,0); makePiece(2,2,4,2,0);
			makePiece(3,3,3,2,0); makePiece(2,4,5,1,0); GameTimer.Counttime=210;
			
		}
		if(game == 8){
			makePiece(2,3,2,1,1); makePiece(3,0,0,1,0); makePiece(2,3,0,2,0); makePiece(2,4,0,2,0); makePiece(3,5,0,2,0);
			makePiece(2,0,1,2,0); makePiece(2,1,1,2,0); makePiece(2,2,1,2,0); makePiece(3,0,3,2,0); makePiece(2,1,4,1,0);
			makePiece(2,1,5,1,0); makePiece(3,3,5,1,0); GameTimer.Counttime=240;
		}
		
		if(game ==9){
			makePiece(2,0,2,1,1); makePiece(2,0,0,2,0); makePiece(3,1,1,1,0); makePiece(2,3,0,1,0); makePiece(2,4,1,2,0);
			makePiece(2,5,0,2,0); makePiece(2,5,2,2,0); makePiece(2,0,3,1,0); makePiece(2,2,2,2,0); makePiece(2,3,2,2,0);
			makePiece(2,3,4,2,0); makePiece(2,4,4,1,0); GameTimer.Counttime=270;
		}
		if(game == 10){
			makePiece(2,0,2,1,1); makePiece(2,0,0,2,0); makePiece(3,2,0,1,0); makePiece(3,5,0,2,0); makePiece(2,1,1,1,0);
			makePiece(2,3,1,2,0); makePiece(3,4,1,2,0); makePiece(2,0,3,1,0); makePiece(2,2,2,2,0); makePiece(2,3,3,2,0);
			makePiece(2,4,4,1,0); makePiece(2,1,5,1,0); makePiece(2,3,5,1,0); GameTimer.Counttime=300;
		}

		
	//	}
	}
	// ���� 1, ���� 2
	private void makePiece(int Psize, int _x, int _y, int vh,int key) {
		// ������ ���� // ���� 4��
		int pieceSize  = Psize;
//		int count = 0;
		Piece piece = new Piece(getGameControlGroup()); // piece�� ���� ��Ʈ�� �̺�Ʈ ����
		// ���� ��Ʈ�� �׷쿡 piece���� �������� ������� �Ѳ����� ��Ʈ�� �ϰ� ���� �װ��� �ϳ��� ����� �Ǽ� ��Ʈ���� �Ǵ°�
		Point point = getRandomPointChoice(_x,_y); // ������� ��ġ  �̰����� �������� ������� ���� ���� ����
		
		addBoundaryToPiece(point, piece);
		pieceSize--;
		//count++;
		//_BoundriesCount--;
		// ���� �ִ� ���� 
		while ((pieceSize > 0)) { // && (_BoundriesCount > 0)
			// 1. ����� �ҷ������Ŀ� ���� ���γ� ���γ�, �ܼ��� �ε����� ������ 
			
			point = getNextRandomPoint(point,vh);
			
			if (point == null) break;
			// 2. �ٿ���� ���� �÷� ����
			addBoundaryToPiece(point, piece);
			pieceSize--;
		//	_BoundriesCount--;
		}
		piece.key = key;
		piece.Mnumber = vh; // ���� ���θ� ��� ���� ������ ����
		piece.Pisize = Psize;
		piece.arrange(); // ���⼭ �ϳ��� ������� ���� ��Ʈ���� ���Ե�
		//////////////////////////////////////////////��ġ ����
		// �����ϰ� ��ġ�ϸ鼭 ���� ��ġ�� �ʵ��� 100�� ���� ��� �Ѵ�.
		//int x=_x, y=_y; // ��ġ ��ǥ
		/*
		for (int i=0; i<100; i++) {
			x = Global.getRandom((Global.screenWidth  / Global.blockSize) - piece.getWidth()); 
			y = Global.getRandom((Global.screenHeight / Global.blockSize) - piece.getHeight()); 

			piece.setPoint(x, y);
			if (checkCollision(piece) == null) break;
		}
		*/
		// ��� �� ���� ��ġ�� üũ ���̺��� true�� �ٲ���
		// �ʱ⼳��
		// 1 . ���� �ϋ�
		piece.CheckAreaT(Psize, _x, _y, vh);
	
		
		piece.setPoint(_x, _y);
		if (_OnNewPiece != null) _OnNewPiece.onNotify(piece);
	}
	// cell �µθ��� ����  
	private void createCells() {
		_Boundaries = new Boundary[Global.boardSize][Global.boardSize];
		
		for (int y=0; y<Global.boardSize; y++) {
			for (int x=0; x<Global.boardSize; x++) {
				_Boundaries[x][y] = new Boundary(x*Global.blockSize, y*Global.blockSize, (x+1)*Global.blockSize, (y+1)*Global.blockSize);
				//_Boundaries[x][y] = new Boundary(y*Global.blockSize, y*Global.blockSize, y*Global.blockSize, y*Global.blockSize);
			}
		}
	}
	
	private void addBoundaryToPiece(Point point, Piece piece) {
		_Boundaries[point.x][point.y] = null;
		piece.addBlock(point.x, point.y);
	}
	// ù ���� ��ŸƮ ��ġ
	private Point getRandomPointChoice(int _x, int _y) {
		//int x = Global.getRandom(Global.boardSize);
		//int y = Global.getRandom(Global.boardSize);
		
		//while (_Boundaries[x][y] == null) {
		//	x = Global.getRandom(Global.boardSize);
		//	y = Global.getRandom(Global.boardSize);
		//}
		
		return new Point(_x, _y);
	}
	
	// �ش� ��ǥ�� Boundary�� ���� �� �� �ִ� ��?  ������ ��ǥ�� ������ 
	private Point getPoint(int x, int y) {
		if ((x < 0) || (x >= Global.boardSize)) return null;
		if ((y < 0) || (y >= Global.boardSize)) return null;
		
		if (_Boundaries[x][y] != null) {
			return new Point(x, y);
		} else {
			return null;
		}
	}
	// ���⼭ �����ϰ� ����Ʈ�� �Ѱ��༭ ���� ������� �Ȱ� ����
	// ������� ����. ���⼭ �⺻ ����Ʈ�� y ���� �����ϸ� ���η� ���������, x���� �����ϸ� ���η� �������
	// �� �ܺο� �������� ������� ��ġ�� ã�ƾ���
	// 2��¥�� ���� ����, ���λ���, 3��¥�� ���λ���, ���� ���� �ؼ� ��� 4���� �żҵ尡 �ʿ�
	// ���� �Լ�, �����Լ� ���� if������ �´°� ȣ��ǰ� �ۼ�
	// �޼ҵ� �̸� �� �ٲٱ�
	private Point getNextRandomPoint(Point basePoint, int vh) {
		ArrayList<Point> points = new ArrayList<Point>();
		
		Point point;
		
//		point = getPoint(basePoint.x-1, basePoint.y);
//		if (point != null) points.add(point); 
//		// �̰Ŷ� 
		if(vh==1){
			point = getPoint(basePoint.x+1, basePoint.y);
			if (point != null) points.add(point); 
		}
		
//		point = getPoint(basePoint.x, basePoint.y-1);
//		if (point != null) points.add(point); 
		// �̰Ŷ� �ʿ� �������� �ʿ� ����
		if(vh == 2){
			point = getPoint(basePoint.x, basePoint.y+1);
			if (point != null) points.add(point); 
		}
		
		if (points.size() == 0) {
			return null;
		} else {
			int index = Global.getRandom(points.size());
			return points.get(index);
		}
	}

	private int getRandomPieceSize() {
		// TODO Auto-generated method stub
		return 6;
	}

	public void setOnNewPiece(OnNotifyEventListener _OnNewPiece) {
		this._OnNewPiece = _OnNewPiece;
	}

	public OnNotifyEventListener getOnNewPiece() {
		return _OnNewPiece;
	}

	private OnNotifyEventListener _OnNewPiece = null;

}
