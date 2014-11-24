package app.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;


public class SelectgameActivity extends Activity implements OnClickListener{
	//Button game1,game2,game3,game4,game5,game6,game7,game8,game9,game10;
	ImageButton game1,game2,game3,game4,game5,game6,game7,game8,game9,game10;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectgame);
		
		//essy

		game1 = (ImageButton)findViewById(R.id.btn1);
		game2 = (ImageButton)findViewById(R.id.btn2);
		game3 = (ImageButton)findViewById(R.id.btn3);
//		// medium
		game4 = (ImageButton)findViewById(R.id.btn4);
		game5 = (ImageButton)findViewById(R.id.btn5);
		game6 = (ImageButton)findViewById(R.id.btn6);
		
//		//hard
		game7 = (ImageButton)findViewById(R.id.btn7);
		game8 = (ImageButton)findViewById(R.id.btn8);
//		//expert
		game9 = (ImageButton)findViewById(R.id.btn9);
		game10 = (ImageButton)findViewById(R.id.btn10);

		
		game1.setOnClickListener(this);
		game2.setOnClickListener(this);
		game3.setOnClickListener(this);
		game4.setOnClickListener(this);
		game5.setOnClickListener(this);
		game6.setOnClickListener(this);
		game7.setOnClickListener(this);
		game8.setOnClickListener(this);
		game9.setOnClickListener(this);
		game10.setOnClickListener(this);
		

		
	}
	public void onClick(View v) {
       Intent it = null;
       
       switch(v.getId()){
       case R.id.btn1:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","1");
    	   startActivity(it);
    	   break;
       case R.id.btn2:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","2");
    	   startActivity(it);
    	   break;
       case R.id.btn3:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","3");
    	   startActivity(it);
    	   break;
       case R.id.btn4:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","4");
    	   startActivity(it);
    	   break;
       case R.id.btn5:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","5");
    	   startActivity(it);
    	   break;
       case R.id.btn6:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","6");
    	   startActivity(it);
    	   break;
       case R.id.btn7:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","7");
    	   startActivity(it);
    	   break;
       case R.id.btn8:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","8");
    	   startActivity(it);
    	   break;
       case R.id.btn9:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","9");
    	   startActivity(it);
    	   break;
       case R.id.btn10:
    	   it = new Intent(this,StartgameActivity.class);
    	   it.putExtra("name","10");
    	   startActivity(it);
    	   break;
       }
    }
	
}