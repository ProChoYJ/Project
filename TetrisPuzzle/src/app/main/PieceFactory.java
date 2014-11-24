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
		createCells(); // 셀을 하나씩 만들고 (경계 태두리)
		makePieces(game); // 만들어진 셀들을 묶는다 2개수에 맞게
	}
	// if 2 이고 1 이면 2개짜리를 가로로 만들어라 이런식으로 루프를 지정해서 만들기
	// 만들어질 위치, 갯수, 가로세로
	private void makePieces(int game) { // 전달 인자 : Bcount, Psize, _X, _Y
			// 입출력 하나 만들어서 변수에 받기
			// 2개짜리 3개짜리 makepice 를 만들고 거기에 _BoundriesCount 배수에 맞게 하면 만들어줄것 
			// 배치하는 코드 만들기
			// _BoundriesCount 는 블락 개수, 이걸가지고 여러개로 나눔 랜덤하게
		//_BoundriesCount = Global.boardSize * Global.boardSize;
	//	_BoundriesCount = 6;
	//	while (_BoundriesCount > 0) {
		//for문을 써서 3개는 3개짜리  만큼 2개짜리 만큼 아니면 switch case를 이용 1탄이면 ~~~ 2탄이면 ~~~
		if(game == 1){
 			makePiece(3,5,0,2,0); // 세로 3개짜리 
			makePiece(2,4,4,1,0); // 가로 3개짜리
			makePiece(2,3,4,2,0); // 가로 3개짜리
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
	// 가로 1, 세로 2
	private void makePiece(int Psize, int _x, int _y, int vh,int key) {
		// 사이즈 조절 // 조각 4개
		int pieceSize  = Psize;
//		int count = 0;
		Piece piece = new Piece(getGameControlGroup()); // piece에 게임 컨트롤 이벤트 적용
		// 게임 컨트롤 그룹에 piece들을 누적으로 저장시켜 한꺼번에 컨트롤 하게 만듬 그것이 하나의 덩어리가 되서 컨트롤이 되는것
		Point point = getRandomPointChoice(_x,_y); // 만들어질 위치  이것으로 전용으로 만들어질 공간 지정 가능
		
		addBoundaryToPiece(point, piece);
		pieceSize--;
		//count++;
		//_BoundriesCount--;
		// 묶어 주는 루프 
		while ((pieceSize > 0)) { // && (_BoundriesCount > 0)
			// 1. 어떤것이 불려오느냐에 따라 세로냐 가로냐, 단순히 인덱스만 가져옴 
			
			point = getNextRandomPoint(point,vh);
			
			if (point == null) break;
			// 2. 바운더리 위에 올려 놓음
			addBoundaryToPiece(point, piece);
			pieceSize--;
		//	_BoundriesCount--;
		}
		piece.key = key;
		piece.Mnumber = vh; // 가로 세로를 등록 무빙 설정을 위해
		piece.Pisize = Psize;
		piece.arrange(); // 여기서 하나의 덩어리가됨 같은 컨트롤을 같게됨
		//////////////////////////////////////////////배치 루프
		// 랜덤하게 배치하면서 서로 겹치지 않도록 100번 정도 노력 한다.
		//int x=_x, y=_y; // 배치 좌표
		/*
		for (int i=0; i<100; i++) {
			x = Global.getRandom((Global.screenWidth  / Global.blockSize) - piece.getWidth()); 
			y = Global.getRandom((Global.screenHeight / Global.blockSize) - piece.getHeight()); 

			piece.setPoint(x, y);
			if (checkCollision(piece) == null) break;
		}
		*/
		// 블락 이 생긴 위치에 체크 테이블을 true로 바꿔논다
		// 초기설정
		// 1 . 가로 일떄
		piece.CheckAreaT(Psize, _x, _y, vh);
	
		
		piece.setPoint(_x, _y);
		if (_OnNewPiece != null) _OnNewPiece.onNotify(piece);
	}
	// cell 태두리를 생성  
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
	// 첫 시작 스타트 위치
	private Point getRandomPointChoice(int _x, int _y) {
		//int x = Global.getRandom(Global.boardSize);
		//int y = Global.getRandom(Global.boardSize);
		
		//while (_Boundaries[x][y] == null) {
		//	x = Global.getRandom(Global.boardSize);
		//	y = Global.getRandom(Global.boardSize);
		//}
		
		return new Point(_x, _y);
	}
	
	// 해당 좌표에 Boundary를 가져 올 수 있는 가?  있으면 좌표를 리턴한 
	private Point getPoint(int x, int y) {
		if ((x < 0) || (x >= Global.boardSize)) return null;
		if ((y < 0) || (y >= Global.boardSize)) return null;
		
		if (_Boundaries[x][y] != null) {
			return new Point(x, y);
		} else {
			return null;
		}
	}
	// 여기서 랜덤하게 포인트를 넘겨줘서 여러 모양으로 옴겨 붙음
	// 순서대로 구현. 여기서 기본 포인트를 y 값만 조정하면 세로로 만들어지고, x값만 조정하면 가로로 만들어짐
	// 맵 외부에 전용으로 만들어질 위치를 찾아야함
	// 2개짜리 가로 생성, 세로생성, 3개짜리 가로생성, 세로 생성 해서 모두 4개의 매소드가 필요
	// 가로 함수, 세로함수 만들어서 if문으로 맞는것 호출되게 작성
	// 메소드 이름 다 바꾸기
	private Point getNextRandomPoint(Point basePoint, int vh) {
		ArrayList<Point> points = new ArrayList<Point>();
		
		Point point;
		
//		point = getPoint(basePoint.x-1, basePoint.y);
//		if (point != null) points.add(point); 
//		// 이거랑 
		if(vh==1){
			point = getPoint(basePoint.x+1, basePoint.y);
			if (point != null) points.add(point); 
		}
		
//		point = getPoint(basePoint.x, basePoint.y-1);
//		if (point != null) points.add(point); 
		// 이거랑 필요 나머지는 필요 없음
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
