package com.example.vinipachecov.timers;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // variable is destroyed after finished ( temporary handler)
        new CountDownTimer(10000, 1000){
            public void onTick(long millisecondsUntilDone){

                //countdown is counting down (every second)

                Log.i("Seconds Left", String.valueOf(millisecondsUntilDone / 1000));
            }

            public void onFinish(){

                //Countedown is finished!
                Log.i("Done!" , "Countdown Timer finished");
            }
        }.start();

//
//        //do things in a controlled way
//        final Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//
//                Log.i("RUnnable has run", "a second must have passed...");
//
//                // insert code here that will run every second 1000ms***
//                handler.postDelayed(this, 1000);
//            }
//        };
//
//        handler.post(run);

    }
}
