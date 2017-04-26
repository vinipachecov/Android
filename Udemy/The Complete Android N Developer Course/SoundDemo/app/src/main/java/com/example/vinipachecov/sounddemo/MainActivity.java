package com.example.vinipachecov.sounddemo;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;

    public void playAudio(View view){



        mediaPlayer.start();
    }


    public void pauseAudio(View view){

        mediaPlayer.stop();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.laugh);



    }
}
