package app.main;



public class GameTimer extends Thread {
	
	
	public static GameTimer gt = null;
	public static int Counttime;
	private boolean flag = false;
	public void run(){
		//Counttime=10;
		while(!GameTimer.interrupted() && !flag){
			try{
				sleep(1000);
				Counttime--;
			}catch(InterruptedException e){  }
			if(Counttime == 0){
				try{
					sleep(3000);
					System.exit(0);
				}catch(Exception e){ }
			}
		}

	}
	public void setFleg(boolean flag){
		this.flag = flag;
	}
	
	

}
