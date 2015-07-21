package com.woowahan.intern.internclient;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {

        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Log.d("swipe", "1");
            //From Right to Left
            return true;
        }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            //From Left to Right
            Log.d("swipe", "2");
            return true;
        }

        if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            //From Bottom to Top
            Log.d("swipe", "3");
            return true;
        }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            //From Top to Bottom
            Log.d("swipe", "4");
            return true;
        }
        return false;
    }
    @Override
    public boolean onDown(MotionEvent e) {
        //always return true since all gestures always begin with onDown and<br>
        //if this returns false, the framework won't try to pick up onFling for example.
        return true;
    }
}