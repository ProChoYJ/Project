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
			
			
			checkGameEnd(); //������ �����ϋ� ���� �����
			//_Paint.setARGB(255, 0, 0, 0);
			//_Score.drawText("�׽�Ʈ : ", 300, 300, _Paint);
		}
	};
	
	public void add(Piece piece) {
		_List.add(piece);
		piece.setOnMoved(_OnPieceMoved);
		
	}
	// ������ �������� ���� true�� ������ ���� �� 
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
