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

	public int Mnumber; // ���� ���� ���� �� ����
	public int Pisize; // ��� ���� 
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
	// �浹�� �ִ��� ������ �˻�
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
				
				paint.setARGB(0, 255, 255, 255); // ����
				paint.setAntiAlias(true);
				
			//}
//		}
		// ù���� ����� �׷� ����� Rect �� bitmap �̶��
				
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
	
	// Action Down, Move �� ��, event �߻� ��ġ
	private Point _TouchDown = new Point();//���� ��ġ�Ȱ�
	private Point _TouchMove = new Point();//��ġ�� �̵�
	/////////////////////////////////////////////////////////////// ��ġ�� �� ��ǥ ����
    private void doActionDown(int x, int y) {
    	_TouchDown.set(x, y);
    	_TouchMove.set(x, y);
    	// �մ����� ���� ������ ���� ���̸� ����
    	int cx = (_X*Global.blockSize)  + (Global.blockSize / 2);
    	int cy = (_Y*Global.blockSize)  + (Global.blockSize / 2); 
    	cx = (cx / Global.blockSize);
    	cy = (cy / Global.blockSize);
    	
    	// ��ġ�� �ִ� ������ �Ǵ� ��� ��ǥ�� ���ؼ� �װ��� false�� 
    	CheckAreaF(Pisize,cx,cy,Mnumber);
   
    }
    // boolean �� �Լ��� ���� cx�� cy�� ���� �ϳ��� 0 �̸� setpoint�� ������� ���� 
    //////////////////////////////////////////////////////// �����ġ�� ���� ������  true��
    boolean checkRight=false; // �浹 ����
    boolean checkLeft=false;
    boolean checkBelow=false;
    boolean checkAbove=false;
    // �簢�� �߽��̱� ������ ���� ���
    private void doActionUp(int x, int y) {
    	// cx �� cy�� �뿩�� ��  �̵��� �Ÿ� 
    	// ��ġ�� ����ġ�� ���� ���� �Ұ�� ���� ����
    	 // �տ� ���� �ִ��� ����
    	int tmp = x - _TouchDown.x;
    	int cx = (_X*Global.blockSize) + (x - _TouchDown.x) + (Global.blockSize / 2);
    	int cy = (_Y*Global.blockSize) + (y - _TouchDown.y) + (Global.blockSize / 2); 
    	
    	//x - _TouchDown.x ���� ������ ����, ����� ������
    	
    	
    	_TouchDown.set(0, 0); // ������ �ʱ�ȭ?
    	_TouchMove.set(0, 0);
    	
    	cx = (cx / Global.blockSize);
    	cy = (cy / Global.blockSize);
    	
    	// ��������� �뿩�� �ּҸ� ���
    	
    	
    	
    	// �ٽ� ������ε��ƿԴ�???
    	// ȭ���� ���� ���ٸ� ���⼭ ���� ����Ʈ�� ����
    	// ���� �϶��� �����϶��� ���� ����
    	if(Mnumber == 1){ // �����ϋ��� ������ cx �� �ʿ� 
    		if(Pisize==2){
    			if(key == 0){
    				if (cx < 0) cx = 0;
	    			//if (cy < 0) cy = 0;
	    			if (cx > 4) cx = 4; // ���� ������ - ��� ũ��
	    			//if (cy > 5) cy = 5; // ���� ������ -1
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
			    	if (cx > 5) cx = 5;  // ���� ������ -1
			    	if (cy > 3) cy = 4; // ���� ������ - ��� ũ��
	    	//if (collision()) return false;
    		}
    		if(Pisize==3){
    			if (cx < 0) cx = 0;
		    	if (cy < 0) cy = 0;
		    	if (cx > 5) cx = 5;  // ���� ������ -1
		    	if (cy > 3) cy = 3;
    		}
	    	
    	}
    	
//    	if (cx > (Global.screenWidth  - getWidth()))  cx = Global.screenWidth  - getWidth(); 		
//    	if (cy > (Global.screenHeight - getHeight())) cy = Global.screenHeight - getHeight();    
    	// 2��¥���ϋ�
    	if(checkRight){// �����
    		CheckAreaT(Pisize,_cx-Pisize,cy,Mnumber);
    		setPoint(_cx-Pisize, cy);
    		checkRight = false; // Ǯ����
    		return;
    	}
    	
    	else if(checkLeft){// �����
    		CheckAreaT(Pisize,_cx+1,cy,Mnumber);
    		setPoint(_cx+1, cy);
    		checkLeft = false; // Ǯ����
    		return;
    	}
    	
    	else if(checkBelow){// �����
    		CheckAreaT(Pisize,cx,_cy-Pisize,Mnumber);
    		setPoint(cx, _cy-Pisize);
    		checkBelow = false; // Ǯ����
    		return;
    	}
    	
    	else if(checkAbove){// �����
    		CheckAreaT(Pisize,cx,_cy+1,Mnumber);
    		setPoint(cx, _cy+1);
    		checkAbove = false; // Ǯ����
    		return;
    	}
    	//if(checkRight==true){
    	else{
    		CheckAreaT(Pisize,cx,cy,Mnumber);
    		setPoint(cx, cy);
    	//}
    	}
    	
    	
    }
    
    // �� �ܺη� ��Ż �޴��� ����
    private boolean doActionUpCheck(int x, int y){
    	int cx = (_X*Global.blockSize) + (x - _TouchDown.x) + (Global.blockSize / 2);
    	int cy = (_Y*Global.blockSize) + (y - _TouchDown.y) + (Global.blockSize / 2); 
    	
    	// ���� ��ġ�� �̵��ɶ����� ��� ���� ���޵� ���� �տ� ����� �־� check �迭�� true(����� ������)�̸� false ���� �ؼ� �������� ���ϰ� 

//    	_TouchDown.set(0, 0); // ������ �ʱ�ȭ?
//    	_TouchMove.set(0, 0);

    	cx = (cx / Global.blockSize);
    	cy = (cy / Global.blockSize);
    	// �ϳ��� �浹�� �Ͼ�� ������ �̵� ���� // �浹�� �Ͼ�� ��� �ڿ� �̵��Ҽ��ִ� ��ŭ ������ ������ �Ѿ�� ���� ����
    	if((checkRight)|(checkLeft)|(checkAbove)|(checkBelow))
    		return false;
    
    	// �ٽ� ������ε��ƿԴ�???
    	// ȭ���� ���� ���ٸ� ���⼭ ���� ����Ʈ�� ����
    	// ���� �϶��� �����϶��� ���� ����, �� �հ� �ڸ� 0.5 ������ ���� �Ͽ� 
    	if(Mnumber == 1){ // ���� �ϋ��� x �ุ ���� // ���� �϶��� x+1 x-1 �� �˻�
    		
    		// 3��¥�� �ϋ� 2��¥���ϋ� ���� �� ���� ���� �ϴ� if �� ���� ����
    		if(Pisize == 2){ // ��ġ�� ���� ���� �˻� �ؾ���
    			
    			
    	    	if( cx < 0) cx =0;
    	    	if(key == 1)
    	    		if(cx > 5) cx = 5;
    	    	if( key == 0)
    	    		if( cx > 4) cx = 4;
    	    	
		    	if(Global.CheckArea[cy][cx+1]==true){
		    		_cx = cx+1;
		    		checkRight=true; // �浹 �߻�
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cx = cx;
		    		checkLeft=true;
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
		    	if( key == 1 ){
		    		if (cx < 0.5) return false;
		    		if(cx > 4.5) return false;
		    	}
		    	if(key==0){
			    	if (cx < 0.5) return false;
			    	if (cy < 0) return false;
			    	if (cx > 3.5) return false; // ���� ������ - ��� ũ�� - 0.5
			    	if (cy > 5) return false; // ���� ������ -1 - 0.5 0.5 �� �߾�
		    	}
    		}else if(Pisize == 3){
    			if (cx < 0) cx = 0;
    			if (cx > 3) cx = 3;
    			
		    	if(Global.CheckArea[cy][cx+2]==true){
		    		_cx = cx+2;
		    		checkRight=true; // �浹 �߻�
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cx = cx;
		    		checkLeft=true;
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
		    	if (cx < 0.5) return false;
		    	if (cy < 0) return false;
		    	if (cx > 2.5) return false; // ���� ������ - ��� ũ�� - 0.5
		    	if (cy > 5) return false; // ���� ������ -1 - 0.5 0.5 �� �߾�
    		}
	    	//if (collision()) return false;
	    	// cx �� 0 ���� Ŭ��쿡�� ��
	    	
    		//if(Global.CheckArea[cy][cx+Pisize]==true) return false;
    	}
    	else if(Mnumber == 2){ // ���� �ϋ��� y ���� ����
    		if(Pisize==2){
    			if( cy > 4) cy = 4;
    	    	if( cy < 0) cy =0;
    			
    	    	if(Global.CheckArea[cy+1][cx]==true){
		    		_cy = cy+1;
		    		checkBelow=true; // �浹 �߻�
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cy = cy;
		    		checkAbove=true;
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
    			
    			if (cx < 0) return false;
		    	if (cy < 0.5) return false;
		    	if (cx > 5) return false;  // ���� ������ -1
		    	if (cy > 3.5) return false; // ���� ������ - ��� ũ��
    		}else if(Pisize==3){
    			
    			if (cy < 0) cy = 0;
    			if (cy > 3) cy = 3;
    			
    			if(Global.CheckArea[cy+2][cx]==true){
		    		_cy = cy+2;
		    		checkBelow=true; // �浹 �߻�
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
		    	if(Global.CheckArea[cy][cx]==true){
		    		_cy = cy;
		    		checkAbove=true;
		    		return false; // �̶� cx ���� ���� ������ �� ������ �浹�� �Ͼ �� ��ġ // ���߿� ������ ������ �Ѿ�� ���� �ص� �浹 �� ���� �̵���Ű�� ����
		    	}
    			
		    	if (cx < 0) return false;
		    	if (cy < 0.5) return false;
		    	if (cx > 5) return false;  // ���� ������ -1
		    	if (cy > 2.5) return false; // ���� ������ - ��� ũ��
    		}
	    	//if (collision()) return false;
    	}
    	return true;
    }
    


    
    ////  �浹 �˻� �տ� ����� �ִ��� ���� �����϶��� x �ุ �˻� , �����ϋ��� y ���˻�
    private boolean collision(){
    	if(checkCollision(this) != null){ // �浹�� �Ͼ�� ���
    		return true;
    	}
    	return false;
    }
    
    // ���� ���� ���� �������� �����ϋ����� ��ġ ����
    // ����� �ִ� ��ġ�� true
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
    	
    	if( vh == 1	){ // �����϶� Y�� ���� �ϰ� x�� ����
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
    // ��ġ�� �Ͼ� �������� ����  // ���� �� ���� �϶� ��� �ٸ� �̺�Ʈ
    protected boolean onTouchEvent(GamePlatformInfo platformInfo, MotionEvent event) {
    	boolean result = false;
    	// �ٲ� �� ��ǥ
    	int x = (int) event.getX();
    	int y = (int) event.getY();
    	// �ٲ���� ��ǥ
    	int _x = _TouchDown.x;
    	int _y = _TouchDown.y;
 
    	
    	
    	
    	
    	switch (event.getAction()) {
	    	case MotionEvent.ACTION_DOWN://ȭ���� ����  false �� �ٲٰ� 
	    		if (getIsMyArea(x, y))	{ // ��ġ�� ��ǥ�� ����� �ִٸ�
	    			
		    		doActionDown(x, y);	
		    	 	
		    		//checkAreaF(Pisize,x,y,Mnumber);
	    	    	_isMoving = true;
		    		result = true;
	    		    bringToFront(); // ���� ������ �Ʒ� ��� �ִ°��� �� ȭ�� ���� �ø��� �޼ҵ�
	    		    //CheckAreaF(Pisize,x,y,Mnumber);
	    		}
	    		break;
	    		
	    	case MotionEvent.ACTION_UP: //ȭ�鿡�� ��
	    		// ��ġ�� ������� ���ƿ���
	    		
	    	
	    		
	    		if (_isMoving) { // true�� �ٲٰ� 
	    			
	    			if(Mnumber == 1){ // ���� �ϋ� ���η� �� �����̰�
	    				//CheckAreaT(Pisize, x,_y,Mnumber);
			    		doActionUp(x, _y); 
			    		
			    		//checkAreaT(Pisize,x,_y,Mnumber);
		    			_isMoving = false;
			    		result = true;
	    			}else if(Mnumber == 2){ // �����϶� ���ηθ� �����̰�
	    				//CheckAreaT(Pisize, _x,y,Mnumber);
	    				doActionUp(_x, y); 
	    				
	    				//checkAreaT(Pisize,x,_y,Mnumber);
		    			_isMoving = false;
			    		result = true;
	    			}
	    			
	    			
	    			
	    		}
	    		break;
	    		// �¿�� �����̵� �ϴ� �� ���� �غ���
	    	case MotionEvent.ACTION_MOVE: // ���� ����� �ִٸ� �������� ���ϰ�
	    		// ( getIsMyArea(x-1, y) || getIsMyArea(x+1, y) || getIsMyArea(x, y-1) || getIsMyArea(x, y+1) )�̸� break(�ƹ� �̺�Ʈ�� �Ͼ�� ����)
	    		// else �� �̵� �Ҽ� �ִٸ� �ξ׼ǹ���
	    		
	    		if (_isMoving) {// ����ä�� ������ _isMoving && �� ���� ���ٸ� // ���� ����� �ִ��� ������ �˻��ϴ� �޼ҵ尡 �ʿ� getIsMyArea(x, y)�� ���ǹ���
	    			if(Mnumber == 1){
		    			if(doActionUpCheck(x,_y) ){ // ���� ������ ������ ������
		    					doActionMove(x,_y);
		    					result = true;
		    			}
	    			}else if(Mnumber == 2){
	    				if(doActionUpCheck(_x,y)){
		    				doActionMove(_x, y);
			    			result = true;
		    			}
	    			}
	    		//	doActionMove(x, y); // �����̵�� ��ä ������ ������ �б�
		    		//result = true;
	    		}
	    		break;
    	}
    	
    	return result;
    }
    // ��ǥ�� ����� �ִٸ�
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
