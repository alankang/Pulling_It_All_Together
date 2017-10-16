package com.example.alan.pulling_it_all_together;

/**
 * Created by alan on 10/12/17.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
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
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private TextView txtMessage;
    private GestureDetectorCompat GD;    //must instantiate the gesture detector
    private ImageView desp;
    private int SWIPE_MIN_DISTANCE=120;
    private int SWIPE_THRESHOLD_VELOCITY=150;
    private int SWIPE_THRESHOLD_VELOCITY_FAST=5000;
    Animation rotateanim, rotateck10, rotateck5, rotatecc10, rotatecc5,MD,MU,ML,MR,shake;
    private static int SIGNIFICANT_SHAKE = 100000;
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private float lastX, lastY, lastZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        GD = new GestureDetectorCompat(this, this);   //Context, Listener as per Constructor Doc.
        GD.setOnDoubleTapListener(this);   //DoubleTaps implemented a bit differently, must be bound like this.



        desp = (ImageView)findViewById(R.id.desp);
        rotateanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        rotatecc5 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotatecounterclock5);
        rotateck5 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotateclock5);
        rotatecc10 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotatecounterclock10);
        rotateck10 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotateclock10);
        MD = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movedown);
        MU = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.moveup);
        ML = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.moveleft);
        MR = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.moveright);
        shake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
    }
    @Override
    protected void onStart() {
        super.onStart();

        enableAccelerometerListening();
    }

    @Override
    protected void onStop() {

        disableAccelerometerListening();
        super.onStop();
    }

    private void enableAccelerometerListening() {
        // The Activity has a SensorManager Reference.
        // This is how we get the reference to the device's SensorManager.
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(
                        Context.SENSOR_SERVICE);    //The last parm specifies the type of Sensor we want to monitor


        //Now that we have a Sensor Handle, let's start "listening" for movement (accelerometer).
        //3 parms, The Listener, Sensor Type (accelerometer), and Sampling Frequency.
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);   //don't set this too high, otw you will kill user's battery.
    }



    // disable listening for accelerometer events
    private void disableAccelerometerListening() {

//Disabling Sensor Event Listener is two step process.
        //1. Retrieve SensorManager Reference from the activity.
        //2. call unregisterListener to stop listening for sensor events
        //THis will prevent interruptions of other Apps and save battery.

        // get the SensorManager
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(
                        Context.SENSOR_SERVICE);

        // stop listening for accelerometer events
        sensorManager.unregisterListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // get x, y, and z values for the SensorEvent
            //each time the event fires, we have access to three dimensions.
            //compares these values to previous values to determine how "fast"
            // the device was shaken.
            //Ref: http://developer.android.com/reference/android/hardware/SensorEvent.html

            float x = event.values[0];   //always do this first
            float y = event.values[1];
            float z = event.values[2];

            // save previous acceleration value
            lastAcceleration = currentAcceleration;

            // calculate the current acceleration
            currentAcceleration = x * x + y * y + z * z;   //This is a simplified calculation, to be real we would need time and a square root.

            // calculate the change in acceleration        //Also simplified, but good enough to determine random shaking.
            acceleration = currentAcceleration *  (currentAcceleration - lastAcceleration);

            // if the acceleration is above a certain threshold
//            if (acceleration > SIGNIFICANT_SHAKE && acceleration<120000) {
//                float deltax =x-lastX;
//                float deltay=y-lastY;
//                float deltaz=z-lastZ;
//                Log.e(TAG, "delta x = " + deltax);
//                Log.e(TAG, "delta y = " + deltay);
//                Log.e(TAG, "delta z = " + deltaz);
//                Toast.makeText(MainActivity.this,"Move on x direction is"+(x-lastX),Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,"Move on y direction is"+(x-lastY),Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,"Move on z direction is"+(x-lastZ),Toast.LENGTH_SHORT).show();
//                float largest=Math.max(Math.max(deltax,deltay),deltaz);
//                if(largest==deltax){
//                    desp.startAnimation(MD);
//
//                }else if(largest==deltay){
//                    desp.startAnimation(MU);
//
//                }else{
//                    desp.startAnimation(ML);
//
//                }
//            }
//            if(acceleration>120000){
//                desp.startAnimation(shake);
//            }
//            if (acceleration > SIGNIFICANT_SHAKE){
//                desp.startAnimation(shake);
//            }
                if(acceleration > SIGNIFICANT_SHAKE){
                    desp.startAnimation(shake);
                }

                else {
                    if (x > 5) {
                        desp.startAnimation(ML);
                    }
                    if (x < -5) {
                        desp.startAnimation(MR);
                    }
                    if (y > 13) {
                        desp.startAnimation(MD);
                    }
                    if (y < 6) {
                        desp.startAnimation(MU);
                    }
                }


//                else  {
//                    desp.startAnimation(ML);
//                }
//                if ((y - lastY) > 5) {
//                    desp.startAnimation(MD);
//                }
//                if ((y - lastY) > -5) {
//                    desp.startAnimation(MU);
//                }


            lastX = x;
            lastY = y;
            lastZ = z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

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
        if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY_FAST){
            desp.startAnimation(rotateck10);
        }else if(e2.getX() - e1.getX() < (0-SWIPE_MIN_DISTANCE) && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY_FAST){
            desp.startAnimation(rotatecc10);
        }else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            desp.startAnimation(rotateck5);
        }else if (e2.getX() - e1.getX() < (0-SWIPE_MIN_DISTANCE) && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            desp.startAnimation(rotatecc5);
        }


        return true;
    }
}
