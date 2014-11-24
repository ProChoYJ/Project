package app.main;

import java.util.ArrayList;

import ryulib.OnNotifyEventListener;
import ryulib.game.GamePlatformInfo;
import android.content.Intent;
import android.graphics.Paint;
import android.widget.Toast;

public class PieceList {
	public static int Moved =0;
	GamePlatformInfo _GamePlatformInfo = null;
	private ArrayList<Piece> _List = new ArrayList<Piece>();
	private Paint _Paint = new Paint();
	public void clear() {
		_List.clear();
	}
	
	private OnNotifyEventListener _OnPieceMoved = new OnNotifyEventListener() {
		@Override
		public void onNotify(Object sender) {
			
			
			checkGameEnd(); //조각을 움직일떄 마다 실행됨
			//_Paint.setARGB(255, 0, 0, 0);
			//_Score.drawText("테스트 : ", 300, 300, _Paint);
		}
	};
	
	public void add(Piece piece) {
		_List.add(piece);
		piece.setOnMoved(_OnPieceMoved);
		
	}
	// 게임이 끝났는지 여부 true가 들어오면 게임 끝 
	public void checkGameEnd() {
		int j=6,count=0;
		for(int i=0;i<Global.boardSize;i++)
			if(Global.CheckArea[i][j])
				count++;
		
		if(count==0){
			Moved++;
			return;
		}
			
		Moved++;
		if (_OnGameEnd != null) _OnGameEnd.onNotify(this);
	}
	
	private OnNotifyEventListener _OnGameEnd = null;
	
	public void setOnGameEnd(OnNotifyEventListener _OnGameEnd) {
		this._OnGameEnd = _OnGameEnd;
	}

	public OnNotifyEventListener getOnGameEnd() {
		return _OnGameEnd;
	}

}
