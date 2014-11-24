package app.main;

import java.util.ArrayList;

import ryulib.OnNotifyEventListener;
import ryulib.game.GameControl;
import ryulib.game.GameControlGroup;
import ryulib.game.GamePlatformInfo;
import ryulib.game.HitArea;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Piece extends GameControl {

	public Piece(GameControlGroup gameControlGroup) {
		super(gameControlGroup);
		
		gameControlGroup.addControl(this);
	}
	
	private HitArea _HitArea = new HitArea();

	public int Mnumber; // 가로 세로 구분 할 변수
	public int Pisize; // 블락 갯수 
	public int _cx;
	public int _cy;
	public int key;
	
	@Override
	protected HitArea getHitArea() {
		return _HitArea;
	}

	private int _X = 0;
	private int _Y = 0;
	private int _MinLeft = 0xFFFF;
	private int _MinTop = 0xFFFF;
	private int _MaxLeft = -1;
	private int _MaxTop = -1;

	ArrayList<Block> _Blocks = new ArrayList<Block>();
	
	public int getWidth() {
		return _MaxLeft - _MinLeft + 1;
	}
		
	public int getHeight() {
		return _MaxTop - _MinTop + 1;
	}
		
	public void clearBlocks() {
		_MinLeft = 0xFFFF;
		_MinTop = 0xFFFF;
		
		_Blocks.clear();
	}

	public void addBlock(int x, int y) {
		if (x < _MinLeft) _MinLeft = x;
		if (y < _MinTop ) _MinTop  = y;
		
		if (x > _MaxLeft) _MaxLeft = x;
		if (y > _MaxTop ) _MaxTop  = y;
		
		Block block = new Block(x, y);
		_Blocks.add(block);
	}

	public void arrange() {
		for (Block block : _Blocks) {
			block.decX(_MinLeft);
			block.decY(_MinTop);
		}

		afterMoved();
	}

	private int _A = 255;
	private int _R = (int) (Math.random() * 100) + 155;
	private int _G = (int) (Math.random() * 100) + 155;
	private int _B = (int) (Math.random() * 100) + 155;
	private Canvas _Canvas = null;
	// 충돌이 있는지 없는지 검사
	@Override
	protected void onDraw(GamePlatformInfo platformInfo) {
		//Paint paint  = platformInfo.getPaint();
		Paint paint = new Paint();
		_Canvas = platformInfo.getCanvas();
//		Bitmap bit =platformInfo.getBitmap();
//	
//		Resources res =  Resources.getSystem();
//		BitmapDrawable bd = (BitmapDrawable)res.getDrawable(R.drawable.up);
//		bit = bd.getBitmap();
		//Bitmap bit = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.down);
		
//		if (_isMoving) {
		//	paint.setARGB(100, _R, _G, _B);
//		} 
//		else {
//			if ((checkCollision(this) != null)) {
//				paint.setARGB(100, _R, _G, _B);
//			} else {
				//paint.setARGB(_A, _R, _G, _B);
				
				paint.setARGB(0, 255, 255, 255); // 투명
				paint.setAntiAlias(true);
				
			//}
//		}
		// 첫번쨰 블락을 그려 줘야함 Rect 가 bitmap 이라면
				
		int left = _X*Global.blockSize + _TouchMove.x-_TouchDown.x;
		int top  = _Y*Global.blockSize + _TouchMove.y-_TouchDown.y;
		//for (Block block : _Blocks) {
		
		
		if(key==1){
			_Blocks.get(0).getBoundary().setBoundary(1, 1, (Global.blockSize-2)*2, (Global.blockSize-2));
			_Canvas.drawBitmap(Global.bit[0], null,_Blocks.get(0).getBoundary().getRect(left, top),null);	
		}
		//if(ResourcePiece.getInstance().getBitmap(1) == null)
//			platformInfo.getCanvas().drawRect(
//					_Blocks.get(0).getBoundary().getRect(left, top), 
//					paint
//			);
			
			
		//}
		else if(key == 0){
			if(Mnumber == 1){
				if(Pisize == 2){
					_Blocks.get(0).getBoundary().setBoundary(1, 1, (Global.blockSize-2)*2, (Global.blockSize-2));
					_Canvas.drawBitmap(Global.bit[2], null,_Blocks.get(0).getBoundary().getRect(left, top),null);
				}
				else if(Pisize == 3){
					_Blocks.get(0).getBoundary().setBoundary(1, 1, (Global.blockSize-2)*3, (Global.blockSize-2));
					 _Canvas.drawBitmap(Global.bit[3], null,_Blocks.get(0).getBoundary().getRect(left, top),null);
				}
			}
			else if(Mnumber == 2){
				if(Pisize == 2){
					_Blocks.get(0).getBoundary().setBoundary(1, 1, (Global.blockSize-2), (Global.blockSize-2)*2);
					 _Canvas.drawBitmap(Global.bit[4], null,_Blocks.get(0).getBoundary().getRect(left, top),null);
				}
				else if(Pisize == 3){
					_Blocks.get(0).getBoundary().setBoundary(1, 1, (Global.blockSize-2), (Global.blockSize-2)*3);
					 _Canvas.drawBitmap(Global.bit[5], null,_Blocks.get(0).getBoundary().getRect(left, top),null);
				}
			}
		}
	   // _Canvas.drawBitmap(Global.bit[0], null,_Blocks.get(0).getBoundary().getRect(left, top),null);
			
	}
	
	// Action Down, Move 일 때, event 발생 위치
	private Point _TouchDown = new Point();//최초 터치된곳
	private Point _TouchMove = new Point();//터치후 이동
	/////////////////////////////////////////////////////////////// 터치한 곳 좌표 설정
    private void doActionDown(int x, int y) {
    	_TouchDown.set(x, y);
    	_TouchMove.set(x, y);
    	// 손댓을대 값과 움직인 값의 차이를 구함
    	int cx = (_X*Global.blockSize)  + (Global.blockSize / 2);
    	int cy = (_Y*Global.blockSize)  + (Global.blockSize / 2); 
    	cx = (cx / Global.blockSize);
    	cy = (cy / Global.blockSize);
    	
    	// 위치에 있는 기준이 되는 블락 좌표를 구해서 그곳은 false로 
    	CheckAreaF(Pisize,cx,cy,Mnumber);
   
    }
    // boolean 형 함수로 만드어서 cx와 cy가 둘중 하나가 0 이면 setpoint를 원래대로 오게 
    //////////////////////////////////////////////////////// 블락위치를 변경 했을때  true값
    boolean checkRight=false; // 충돌 여부
    boolean checkLeft=false;
    boolean checkBelow=false;
    boolean checkAbove=false;
    // 사각형 중신이기 떄문에 조금 벗어남
    private void doActionUp(int x, int y) {
    	// cx 와 cy는 노여질 값  이동한 거리 
    	// 터치가 지나치게 많이 오바 할경우 수를 제한
    	 // 앞에 벽이 있는지 여부
    	int tmp = x - _TouchDown.x;
    	int cx = (_X*Global.blockSize) + (x - _TouchDown.x) + (Global.blockSize / 2);
    	int cy = (_Y*Global.blockSize) + (y - _TouchDown.y) + (Global.blockSize / 2); 
    	
    	//x - _TouchDown.x 값이 음수면 왼쪽, 양수면 오른쪽
    	
    	
    	_TouchDown.set(0, 0); // 움직임 초기화?
    	_TouchMove.set(0, 0);
    	
    	cx = (cx / Global.blockSize);
    	cy = (cy / Global.blockSize);
    	
    	// 여기까지가 노여진 주소를 계산
    	
    	
    	
    	// 다시 원래대로돌아왔다???
    	// 화면을 벗어 난다면 여기서 이제 포인트를 조정
    	// 가로 일때와 세로일때의 무빙 설정
    	if(Mnumber == 1){ // 가로일떄는 어차피 cx 만 필요 
    		if(Pisize==2){
    			if(key == 0){
    				if (cx < 0) cx = 0;
	    			//if (cy < 0) cy = 0;
	    			if (cx > 4) cx = 4; // 보드 사이즈 - 블락 크기
	    			//if (cy > 5) cy = 5; // 보드 사이즈 -1
    			}
    			if(key == 1){
    				if (cx < 0) cx = 0;
    				if(cx > 5) cx = 5;
    			}
    		
    		}
    		if(Pisize==3){
    			if (cx < 0) cx = 0;
    			if (cx > 3) cx = 3;
    		}
	    //	if (collision()) cx = 1;
	    	
    	}
    	else if(Mnumber == 2){
    		if(Pisize==2){
			    	if (cx < 0) cx = 0;
			    	if (cy < 0) cy = 0;
			    	if (cx > 5) cx = 5;  // 보드 사이즈 -1
			    	if (cy > 3) cy = 4; // 보드 사이즈 - 블락 크기
	    	//if (collision()) return false;
    		}
    		if(Pisize==3){
    			if (cx < 0) cx = 0;
		    	if (cy < 0) cy = 0;
		    	if (cx > 5) cx = 5;  // 보드 사이즈 -1
		    	if (cy > 3) cy = 3;
    		}
	    	
    	}
    	
//    	if (cx > (Global.screenWidth  - getWidth()))  cx = Global.screenWidth  - getWidth(); 		
//    	if (cy > (Global.screenHeight - getHeight())) cy = Global.screenHeight - getHeight();    
    	// 2개짜리일떄
    	if(checkRight){// 잠궈줌
    		CheckAreaT(Pisize,_cx-Pisize,cy,Mnumber);
    		setPoint(_cx-Pisize, cy);
    		checkRight = false; // 풀어줌
    		return;
    	}
    	
    	else if(checkLeft){// 잠궈줌
    		CheckAreaT(Pisize,_cx+1,cy,Mnumber);
    		setPoint(_cx+1, cy);
    		checkLeft = false; // 풀어줌
    		return;
    	}
    	
    	else if(checkBelow){// 잠궈줌
    		CheckAreaT(Pisize,cx,_cy-Pisize,Mnumber);
    		setPoint(cx, _cy-Pisize);
    		checkBelow = false; // 풀어줌
    		return;
    	}
    	
    	else if(checkAbove){// 잠궈줌
    		CheckAreaT(Pisize,cx,_cy+1,Mnumber);
    		setPoint(cx, _cy+1);
    		checkAbove = false; // 풀어줌
    		return;
    	}
    	//if(checkRight==true){
    	else{
    		CheckAreaT(Pisize,cx,cy,Mnumber);
    		setPoint(cx, cy);
    	//}
    	}
    	
    	
    }
    
    // 맵 외부로 이탈 햇는지 여부
    private boolean doActionUpCheck(int x, int y){
    	int cx = (_X*Global.blockSize) + (x - _TouchDown.x) + (Global.blockSize / 2);
    	int cy = (_Y*Global.blockSize) + (y - _TouchDown.y) + (Global.blockSize / 2); 
    	
    	// 현재 터치가 이동될때마다 계속 값이 전달됨 만약 앞에 블락이 있어 check 배열에 true(블락이 있으면)이면 false 리턴 해서 움직이지 못하게 

//    	_TouchDown.set(0, 0); // 움직임 초기화?
//    	_TouchMove.set(0, 0);

    	cx = (cx / Global.blockSize);
    	cy = (cy / Global.blockSize);
    	// 하나라도 충돌이 일어나면 무조건 이동 중지 // 충돌이 일어나도 블락 뒤에 이동할수있는 만큼 공간이 있으면 넘어가는 것을 방지
    	if((checkRight)|(checkLeft)|(checkAbove)|(checkBelow))
    		return false;
    
    	// 다시 원래대로돌아왔다???
    	// 화면을 벗어 난다면 여기서 이제 포인트를 조정
    	// 가로 일때와 세로일때의 무빙 설정, 차 앞과 뒤만 0.5 앞으로 설정 하여 
    	if(Mnumber == 1){ // 가로 일떄는 x 축만 설정 // 가로 일때는 x+1 x-1 만 검사
    		
    		// 3개짜리 일떄 2개짜리일떄 가로 끝 범위 조절 하는 if 문 으로 묶기
    		if(Pisize == 2){ // 겹치는 것을 먼저 검사 해야함
    			
    			
    	    	if( cx < 0) cx =0;
    	    	if(key == 1)
    	    		if(cx > 5) cx = 5;
    	    	if( key == 0)
    	    		if( cx > 4) cx = 4;
    	    	
		    	if(Global.CheckArea[cy][cx+1]==true){
		    		_cx = cx+1;
		    		checkRight=true; // 충돌 발생
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cx = cx;
		    		checkLeft=true;
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
		    	if( key == 1 ){
		    		if (cx < 0.5) return false;
		    		if(cx > 4.5) return false;
		    	}
		    	if(key==0){
			    	if (cx < 0.5) return false;
			    	if (cy < 0) return false;
			    	if (cx > 3.5) return false; // 보드 사이즈 - 블락 크기 - 0.5
			    	if (cy > 5) return false; // 보드 사이즈 -1 - 0.5 0.5 는 중앙
		    	}
    		}else if(Pisize == 3){
    			if (cx < 0) cx = 0;
    			if (cx > 3) cx = 3;
    			
		    	if(Global.CheckArea[cy][cx+2]==true){
		    		_cx = cx+2;
		    		checkRight=true; // 충돌 발생
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cx = cx;
		    		checkLeft=true;
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
		    	if (cx < 0.5) return false;
		    	if (cy < 0) return false;
		    	if (cx > 2.5) return false; // 보드 사이즈 - 블락 크기 - 0.5
		    	if (cy > 5) return false; // 보드 사이즈 -1 - 0.5 0.5 는 중앙
    		}
	    	//if (collision()) return false;
	    	// cx 가 0 보다 클경우에만 ㄴ
	    	
    		//if(Global.CheckArea[cy][cx+Pisize]==true) return false;
    	}
    	else if(Mnumber == 2){ // 세로 일떄는 y 값만 설정
    		if(Pisize==2){
    			if( cy > 4) cy = 4;
    	    	if( cy < 0) cy =0;
    			
    	    	if(Global.CheckArea[cy+1][cx]==true){
		    		_cy = cy+1;
		    		checkBelow=true; // 충돌 발생
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cy = cy;
		    		checkAbove=true;
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
    			
    			if (cx < 0) return false;
		    	if (cy < 0.5) return false;
		    	if (cx > 5) return false;  // 보드 사이즈 -1
		    	if (cy > 3.5) return false; // 보드 사이즈 - 블락 크기
    		}else if(Pisize==3){
    			
    			if (cy < 0) cy = 0;
    			if (cy > 3) cy = 3;
    			
    			if(Global.CheckArea[cy+2][cx]==true){
		    		_cy = cy+2;
		    		checkBelow=true; // 충돌 발생
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cy = cy;
		    		checkAbove=true;
		    		return false; // 이때 cx 값을 따로 저장을 해 놔야함 충돌이 일어날 그 위치 // 나중에 무조건 범위를 넘어가는 짓을 해도 충돌 전 으로 이동시키기 위해
		    	}
    			
		    	if (cx < 0) return false;
		    	if (cy < 0.5) return false;
		    	if (cx > 5) return false;  // 보드 사이즈 -1
		    	if (cy > 2.5) return false; // 보드 사이즈 - 블락 크기
    		}
	    	//if (collision()) return false;
    	}
    	return true;
    }
    


    
    ////  충돌 검사 앞에 블락이 있는지 점사 가로일때는 x 축만 검사 , 세로일떄는 y 만검사
    private boolean collision(){
    	if(checkCollision(this) != null){ // 충돌이 일어낫을 경우
    		return true;
    	}
    	return false;
    }
    
    // 가로 세로 현재 조각들이 움직일떄마다 위치 파학
    // 블락이 있는 위치만 true
    protected void CheckAreaT(int Psize, int x, int y, int vh){
    	
    	if( vh == 1	){
	    	for(int i=0; i<Psize; i++){
	    		Global.CheckArea[y][x++] = true;
	    	}
    	}else if( vh == 2){
    		for(int i=0; i<Psize; i++){
	    		Global.CheckArea[y++][x] = true;
	    	}
    	}
	    	
    }
    
  protected void CheckAreaF(int Psize, int x, int y, int vh){
    	
    	if( vh == 1	){ // 가로일때 Y는 고정 하고 x만 변함
	    	for(int i=0; i<Psize; i++){
	    		Global.CheckArea[y][x++] = false;
	    	}
    	}else if( vh == 2){
    		for(int i=0; i<Psize; i++){
	    		Global.CheckArea[y++][x] = false;
	    	}
    	}
	    	
    }
    
 
    
	private void doActionMove(int x, int y) {
		_TouchMove.set(x, y);
    }
    
	private boolean _isMoving = false;
	
    @Override
    // 터치가 일어 났을때의 반응  // 가로 와 세로 일때 모두 다른 이벤트
    protected boolean onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
    	boolean result = false;
    	// 바뀔 후 좌표
    	int x = (int) event.getX();
    	int y = (int) event.getY();
    	// 바뀌기전 좌표
    	int _x = _TouchDown.x;
    	int _y = _TouchDown.y;
 
    	
    	
    	
    	
    	switch (event.getAction()) {
	    	case MotionEvent.ACTION_DOWN://화면을 누름  false 로 바꾸고 
	    		if (getIsMyArea(x, y))	{ // 터치한 좌표에 블락이 있다면
	    			
		    		doActionDown(x, y);	
		    	 	
		    		//checkAreaF(Pisize,x,y,Mnumber);
	    	    	_isMoving = true;
		    		result = true;
	    		    bringToFront(); // 겹쳐 있을시 아래 깔려 있는것을 맨 화면 위로 올리는 메소드
	    		    //CheckAreaF(Pisize,x,y,Mnumber);
	    		}
	    		break;
	    		
	    	case MotionEvent.ACTION_UP: //화면에서 뗌
	    		// 겹치면 원래대로 돌아오기
	    		
	    	
	    		
	    		if (_isMoving) { // true로 바꾸고 
	    			
	    			if(Mnumber == 1){ // 가로 일떄 가로로 만 움직이게
	    				//CheckAreaT(Pisize, x,_y,Mnumber);
			    		doActionUp(x, _y); 
			    		
			    		//checkAreaT(Pisize,x,_y,Mnumber);
		    			_isMoving = false;
			    		result = true;
	    			}else if(Mnumber == 2){ // 세로일때 세로로만 움직이게
	    				//CheckAreaT(Pisize, _x,y,Mnumber);
	    				doActionUp(_x, y); 
	    				
	    				//checkAreaT(Pisize,x,_y,Mnumber);
		    			_isMoving = false;
			    		result = true;
	    			}
	    			
	    			
	    			
	    		}
	    		break;
	    		// 좌우로 슬라이딩 하는 것 구현 해보기
	    	case MotionEvent.ACTION_MOVE: // 옆에 브락이 있다면 움직이지 못하게
	    		// ( getIsMyArea(x-1, y) || getIsMyArea(x+1, y) || getIsMyArea(x, y-1) || getIsMyArea(x, y+1) )이면 break(아무 이벤트가 일어나지 않음)
	    		// else 즉 이동 할수 있다면 두액션무브
	    		
	    		if (_isMoving) {// 누른채로 움직임 _isMoving && 양 옆에 없다면 // 옆에 블락이 있는지 없는지 검사하는 메소드가 필요 getIsMyArea(x, y)을 조건문에
	    			if(Mnumber == 1){
		    			if(doActionUpCheck(x,_y) ){ // 범위 박으로 나가지 않으면
		    					doActionMove(x,_y);
		    					result = true;
		    			}
	    			}else if(Mnumber == 2){
	    				if(doActionUpCheck(_x,y)){
		    				doActionMove(_x, y);
			    			result = true;
		    			}
	    			}
	    		//	doActionMove(x, y); // 슬라이드로 대채 누르고 있을떄 밀기
		    		//result = true;
	    		}
	    		break;
    	}
    	
    	return result;
    }
    // 좌표에 블락이 있다면
	private boolean getIsMyArea(int x, int y) {
		for (Block block : _Blocks) {
			if (block.getBoundary(_X, _Y).isMyArea(x, y)) return true;
		}
		return false;
	}
	
	private OnNotifyEventListener _OnMoved = null;
	
	public OnNotifyEventListener getOnMoved() {
		return _OnMoved;
	}

	public void setOnMoved(OnNotifyEventListener _OnMoved) {
		this._OnMoved = _OnMoved;
	}

	private void afterMoved() {
		_HitArea.clear();
		for (Block block : _Blocks) {			
			_HitArea.add(block.getBoundary(_X, _Y));
		}
		
		if (_OnMoved != null) _OnMoved.onNotify(this);
	}
	
	public void setPoint(int x, int y) {		
		_X = x;
		_Y = y;
		afterMoved();
	}

	public void setX(int _X) {
		this._X = _X;
		afterMoved();
	}

	public int getX() {
		return _X;
	}

	public void setY(int _Y) {
		this._Y = _Y;
		afterMoved();
	}

	public int getY() {
		return _Y;
	}
	
    
}
