package com.example.alan.pulling_it_all_together;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.widget.ImageView;
import android.widget.TextView;

///////  Set of imports for Gesture Detection ////////
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;
//////////////////////////////////////////////////////
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private TextView txtMessage;
    private GestureDetectorCompat GD;    //must instantiate the gesture detector
    private ImageView cat2;
    private int SWIPE_MIN_DISTANCE=120;
    private int SWIPE_THRESHOLD_VELOCITY=150;
    Animation rotateanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GD = new GestureDetectorCompat(this, this);   //Context, Listener as per Constructor Doc.
        GD.setOnDoubleTapListener(this);   //DoubleTaps implemented a bit differently, must be bound like this.



        cat2 = (ImageView)findViewById(R.id.cat2);
        rotateanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
    }

    //////////////////////////////////////////////////////////////////////////
    //very important step, otherwise we won't be able to capture our touches//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);               //Our GD will not automatically receive Android Framework Touch notifications.
        // Insert this line to consume the touch event locally by our GD,
        // IF YOU DON'T insert this before the return, our GD will not receive the event, and therefore won't do anything.
        return super.onTouchEvent(event);          // Do this last, why?
    }
    //////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return true;
    }


    @Override
    public boolean onDoubleTap(MotionEvent e) {
        cat2.startAnimation(rotateanim);




        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            Intent intent = new Intent("com.example.sse.myapplication.MainActivity2");
            startActivity(intent);
        }


        return true;
    }
}
