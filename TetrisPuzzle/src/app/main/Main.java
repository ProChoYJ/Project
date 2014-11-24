package app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Main extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startgame);
      
        
        ImageButton button = (ImageButton) findViewById(R.id.btnstart);
        ImageButton button2 = (ImageButton) findViewById(R.id.btnhelp);
        
        button.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent it = new Intent(Main.this, SelectgameActivity.class);
        		startActivity(it);
        	}
        });
        
        button2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent it = new Intent(Main.this, Help.class);
        		startActivity(it);
        	}
        });
       
        
    }
        

	private TetrisBoard _TetrisBoard = null;
	
}