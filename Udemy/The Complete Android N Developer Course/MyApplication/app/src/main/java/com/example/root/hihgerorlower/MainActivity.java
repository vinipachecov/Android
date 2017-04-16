package com.example.root.hihgerorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int randomNumber;

    public void makeToast(String string){


        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    public void guess(View view){
        EditText  input = (EditText)findViewById(R.id.guessEditText);

        int guessInt = Integer.parseInt(input.getText().toString());

        if (guessInt > randomNumber){
            makeToast("Lower");
        }
        else if (guessInt < randomNumber){
            makeToast("Higher");
        } else {
            makeToast("That's right! Try Again");

            Random rand = new Random();
            randomNumber = rand.nextInt(20) + 1;
        }


//        Toast.makeText(MainActivity.this, input.getText().toString(), Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //random number generated

        Random rand = new Random();

        randomNumber = rand.nextInt(20) + 1;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
