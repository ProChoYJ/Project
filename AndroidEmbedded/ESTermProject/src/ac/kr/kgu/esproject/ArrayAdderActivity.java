package ac.kr.kgu.esproject;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
	

public class ArrayAdderActivity extends Activity {
	ArrayAdapter<CharSequence> adspin;
	Button bt,bt_check,bt_clear;
	String msg,elements="";
	int count;
	public static int sum=0;
	TextView tv,tv_result;
	int element []; Random random;
	View result;
	
	SegwaitThread sw = new SegwaitThread();
	SegprintThread sp = new SegprintThread();
	BuzzerThread but = new BuzzerThread();
	
	public native int BuzzerControl(int value);
	public native int SegmentControl(int value);
	public native int SegmentIOControl(int value);
	public native int Checkresult(int value[],int result);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_array_adder);
		System.loadLibrary("Checkresult");
		System.loadLibrary("buzzer2");
		System.loadLibrary("7segment2");
		
		sum=100;
		// ������ �迭�� �Ѱ� �ַ� �ҽ� �迭 ���� �ϱ� ���� �Ѱ��ְԵǸ� 
		// ������ ���⶧���� ������� Check ���� jni�� ��������ϴ�.
		//sw.setFlag(true);
		//but.setFlag(true);
		//but.setFlag(true);
		sw.setDaemon(true);
		//but.stop();
		sw.start();
		but.setDaemon(true);
		but.start();
		
		//SegmentIOControl(0);
		//SegmentControl(100);
		
		result = findViewById(R.id.result);
		tv = (TextView)findViewById(R.id.Test);
		
		bt = (Button)findViewById(R.id.createbtn);
		Spinner spin = (Spinner)findViewById(R.id.spinner);
		spin.setPrompt("Select");
		
		adspin = ArrayAdapter.createFromResource(this, R.array.Select, android.R.layout.simple_spinner_item);
		adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adspin);
		spin.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				msg = (String)parent.getItemAtPosition(position);
				count = Integer.parseInt(msg);
			}
			public void onNothingSelected(AdapterView<?> parent){
			}
		});
		
		
		
		
		
		bt.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				element = new int [count];
				for(int i=0; i<count;i++)
					element[i] = (int)(Math.random()*(count+1));
				for(int i=0; i<count;i++)
					elements += "�迭 ��� #" + (i+1) + ":" + element[i] +"\n";
				tv.setText(elements);
				result.setVisibility(View.VISIBLE);
				
				
			}
		});
		tv_result = (TextView)findViewById(R.id.retext);
		findViewById(R.id.addbtn).setOnClickListener(mClickListener);
		findViewById(R.id.clearbtn).setOnClickListener(mClickListener);
		
		
	
	}
	
	Button.OnClickListener mClickListener = new Button.OnClickListener(){
		public void onClick(View v){
			Intent it; 
			switch(v.getId()){
			case R.id.addbtn:
				// �ʿ� ����
				//for(int i=0; i<element.length; i++)
				//	sum += element[i];
				//sw.setFlag(true);
				EditText edit = (EditText)findViewById(R.id.resultedit);
				String str = edit.getText().toString();//�Է¹��� �����
				sum = Integer.parseInt(str);
				 // ������ ��� �ϱ� ���� sw �����带 ����
				//but.setFlag(false);
				// �����̸�
				if(Checkresult(element,sum) == 1){
					//but.setDaemon(true);
					but.setOnOff(0);
					//but.start();
					tv_result.setText("�����Դϴ�.");
					
					//sp.setDaemon(true);
					//sp.start();
					//BuzzerControl(0); // �ֱ��� �� ����
					
				}
				else if(Checkresult(element,sum)==0){
					
					//sp.setDaemon(true);
					//sp.start();
					//BuzzerControl(1); // �׳� ��
					//but.setDaemon(true);
					but.setOnOff(1);
					//BuzzerControl(1);
					//but.start();
					msg="";
					count=0;
					tv_result.setText("Ʋ�Ƚ��ϴ�.");
				}
				
			
				break;
			case R.id.clearbtn:
				//sw.setFlag(true);
				but.setOnOff(2);
				//but.start();
				//but.setFlag(true);
				//but.stop();
				//sw.stop();
				
				elements="";
				sum=100;
				result.setVisibility(View.INVISIBLE);
				
				//sp.setFlag(true); // ȭ�� ����
				//BuzzerControl(2); // ���� ����
				//BuzzerControl(2);
				
				//it = new Intent(ArrayAdderActivity.this,ArrayAdderActivity.class);
				//startActivity(it);
				//finish();
				break;
			}
		}
	};
	
	
	class SegwaitThread extends Thread{
		private boolean wait = false;
		
		public void run(){
			while(!wait){
				SegmentIOControl(0);
				SegmentControl(sum);
			}
		}
		public void setFlag(boolean wait){
			this.wait = wait;
		}
	}
	
	class SegprintThread extends Thread{
		private boolean flag = false;
		
		public void run(){
			while(!flag){
				//SegmentIOControl(0);
				//SegmentControl(sum);
			}
		}
		public void setFlag(boolean flag){
			this.flag = flag;
		}
	}
	
	class BuzzerThread extends Thread{
		private int OnOff = 2;
		
		private boolean flag = false;
		public void run(){
			while(!flag){
			BuzzerControl(OnOff);
			}
			
		}
		public void setOnOff(int value){
			this.OnOff = value;
		}
		public void setFlag(boolean flag){
			this.flag = flag;
		}
	}

}

