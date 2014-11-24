package app.main;

import ryulib.OnNotifyEventListener;
import ryulib.game.ImageButton;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;


public class StartgameActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
//        _GamePlatform = new GamePlatform(this);
//        _GamePlatform.setUseMotionEvent(true);
//        _GamePlatform.setLayoutParams(
//        		new LinearLayout.LayoutParams(
//        				ViewGroup.LayoutParams.WRAP_CONTENT,
//        				ViewGroup.LayoutParams.WRAP_CONTENT,
//        				0.0F
//        		)
//        );
//        setContentView(_GamePlatform);
        
       // setContentView(R.layout.main);
      
        Intent it = getIntent();
        gamepan = it.getExtras().getString("name");
        pan = Integer.parseInt(gamepan);
        bld = new AlertDialog.Builder(this);
        bld.setTitle("축하합니다!");
        bld.setMessage("눈더미에서 탈출 했습니다!");
        bld.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.exit(0);
				
			}
		});
        
        GameTimer.gt = new GameTimer();
        
        Platform.createInstance(this);
       
        Resources rs = getResources();
        BitmapDrawable bd = (BitmapDrawable)rs.getDrawable(R.drawable.two_blocks_core);
        Global.bit[0] = bd.getBitmap();
        
        Resources rs2 = getResources();
        BitmapDrawable bd2 = (BitmapDrawable)rs2.getDrawable(R.drawable.background);
        Global.bit[1] = bd2.getBitmap();
        
        Resources rs3 = getResources();
        BitmapDrawable bd3 = (BitmapDrawable)rs3.getDrawable(R.drawable.blocksh2);
        Global.bit[2] = bd3.getBitmap();
        
        Resources rs4 = getResources();
        BitmapDrawable bd4 = (BitmapDrawable)rs4.getDrawable(R.drawable.blocksh3);
        Global.bit[3] = bd4.getBitmap();
        
        Resources rs5 = getResources();
        BitmapDrawable bd5 = (BitmapDrawable)rs5.getDrawable(R.drawable.blocksv2);
        Global.bit[4] = bd5.getBitmap();
        
        Resources rs6 = getResources();
        BitmapDrawable bd6 = (BitmapDrawable)rs6.getDrawable(R.drawable.blocksv3);
        Global.bit[5] = bd6.getBitmap();
        
        
        _TetrisBoard = new TetrisBoard(Platform.Instance.getGameControlGroup(),pan);
       // Button.createInstance();
       // ResetButton.createInstance();	
        
        // 쓰레드
        
        
        Instance = new ImageButton(Platform.Instance.getGameControlGroup());
        End = new ImageButton(Platform.Instance.getGameControlGroup());
        
        // 재시작
		Instance.setPosition((getWidth()*8)/10,(getHeigh()*6)/10);
		Instance.setImageUp(R.drawable.reseton);
		Instance.setImageDown(R.drawable.resetoff);
		Instance.setOnClick(				
				new OnNotifyEventListener() {
					@Override
					public void onNotify(Object sender) {
						
						GameTimer.gt.interrupt();
						GameTimer.gt.setFleg(true);
						Reset();
					}
			    }		
		);
		// 끝내기
		End.setPosition((getWidth()*8)/10, (getHeigh()*8)/10);
		End.setImageUp(R.drawable.endon);
		End.setImageDown(R.drawable.endoff);
		End.setOnClick(				
				new OnNotifyEventListener() {
					@Override
					public void onNotify(Object sender) {
						System.exit(0);
					}
			    }		
		);
		
   
    }

//	private GamePlatform _GamePlatform = null;
    public static boolean Time= false;
    public static AlertDialog.Builder bld = null;
    
    private Canvas _Canvas = null;
    public static ImageButton Instance = null;	// 버튼
    public static ImageButton End = null;
	private TetrisBoard _TetrisBoard = null;
	private static String gamepan="";
	private int pan = 0;
	public void Reset(){
		PieceList.Moved = 0;
		Intent it = new Intent(this,StartgameActivity.class);
		it.putExtra("name",gamepan);
		startActivity(it);
		//_TetrisBoard = new TetrisBoard(Platform.Instance.getGameControlGroup(),pan);
		finish();
	}
	public int getWidth(){
		  
		  WindowManager wm =(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		  Display display = wm.getDefaultDisplay();
		  int Width = display.getWidth();
		  return Width;  
	}
	
	public int getHeigh(){
		  
		  WindowManager wm =(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		  Display display = wm.getDefaultDisplay();
		  int Height = display.getHeight();
		  return Height;  
	}
	
	
}