package com.example.root.animations;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void fade(View view){

        ImageView bart = (ImageView) findViewById(R.id.bart);
//
//        bart.animate().translationXBy(-1000f).setDuration(2000);

        bart.animate().translationXBy(1000f).translationYBy(1000f).rotation(3600).setDuration(2000);

        ImageView homer = (ImageView) findViewById(R.id.homer);

       // bart.animate().translationXBy(1000f).setDuration(2000);

        //bart.animate().alpha(0f).setDuration(2000);



        //homer.animate().alpha(1f).setDuration(2000);

        //ROTATION BART

        //bart.animate().rotation(1800f).setDuration(2000);

        //SHRINK - half the size

        //bart.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //challenge

        ImageView bart = (ImageView) findViewById(R.id.bart);

        bart.setTranslationX(-1000f);
        bart.setTranslationY(-1000f);

        /*


        ImageView bart = (ImageView) findViewById(R.id.bart);

        bart.setTranslationX(-1000f);
        */
    }
}
