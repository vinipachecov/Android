package com.example.vinipachecov.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView timerTextview;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft){
        int minutes = (int)secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

//        if(secondString == "0"){
//            secondString = "00";
//        }
        if(seconds <= 9){
            secondString = "0"+ secondString;
        }
        timerTextview.setText(Integer.toString(minutes) + ":" +secondString);
    }

    public void resetTimer(){
        timerTextview.setText("0:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        timerSeekbar.setEnabled(true);
        counterIsActive = false;
    }


    public void controlTimer(View v){

        if(counterIsActive == false) {


            counterIsActive = true;
            timerSeekbar.setEnabled(false);

            controllerButton.setText("Stop");
            //Log.i("Button Presed", "Pressed");
            // first param is the number o seconds so multiply by 1000
          countDownTimer =  new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);

                }


                @Override
                public void onFinish() {
                    Log.i("finished", "timer done");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    try {
                        this.wait(1000);
                    }catch ( Exception e) {

                    }
                    resetTimer();

                }

            }.start();
        }else{
            resetTimer();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextview = (TextView)findViewById(R.id.timerTextView);
        timerSeekbar = (SeekBar)findViewById(R.id.timerSeekBar);
        controllerButton = (Button)findViewById(R.id.controllerButton);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);


        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
