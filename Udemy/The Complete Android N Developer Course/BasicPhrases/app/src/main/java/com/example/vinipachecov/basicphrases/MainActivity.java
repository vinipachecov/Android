package com.example.vinipachecov.basicphrases;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void buttonTapped(View view){

        int id = view.getId();
        String ourid = "";

        ourid = view.getResources().getResourceEntryName(id);

        int resourceId = getResources().getIdentifier(ourid, "raw","com.example.vinipachecov.basicphrases");

//        For only a particular resource
//        MediaPlayer mplayer = MediaPlayer.create(this, R.raw.doyouspeakenglish);
        MediaPlayer mplayer = MediaPlayer.create(this, resourceId);
        mplayer.start();

        Log.i("Button tapped", ourid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
