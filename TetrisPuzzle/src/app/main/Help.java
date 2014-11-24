package app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Help extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
      
        
        ImageButton button = (ImageButton)findViewById(R.id.back);
        
        button.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent it = new Intent(Help.this, Main.class);
        		startActivity(it);
        		finish();
        	}
        });
       
        
    }
        

	
}