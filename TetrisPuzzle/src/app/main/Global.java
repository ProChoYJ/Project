package app.main;

import java.util.Random;

import android.graphics.Bitmap;

public class Global {
	
	public static void setScreenSize(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		
		blockSize = screenHeight / boardSize;
		
		for (int y=0; y<Global.boardSize+2; y++) {
			for (int x=0; x<Global.boardSize+2; x++) {
				CheckArea[x][y] = false;
			}
		}
	}
	public static Bitmap bit[] = new Bitmap[6];
	public static int screenWidth = 480;

	public static int screenHeight = 320;
	
	public static int blockSize = 24;

	// BoardSize*BoardSize 크기의 배열
	public static int boardSize  = 6;
	
	private static final Random _Random = new Random();
	
	public static boolean CheckArea[][] = new boolean[Global.boardSize+2][Global.boardSize+2];
	
	public static int getRandom(int n) {
		return _Random.nextInt(n);
	}

}