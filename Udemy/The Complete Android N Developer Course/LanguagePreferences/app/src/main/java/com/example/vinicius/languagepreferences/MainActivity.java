package com.example.vinicius.languagepreferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView textView;

//    add option menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

//        associate file to the current menu
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.english ){

            setLanguage("English");

        }else if (item.getItemId() == R.id.spanish){

            setLanguage("Spanish");
        }


        return true;
    }

    public void setLanguage(String language) {


//        saving shared preferences
        sharedPreferences.edit().putString("language", language).apply();

        Log.i("Language", language);


        textView.setText(language);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);


        //accessing shared preferences
        sharedPreferences = this.getSharedPreferences("com.example.vinicius.languagepreferences", Context.MODE_PRIVATE);

        String language = sharedPreferences.getString("language", "");

//        only display the dialog if there isn't a preference

        if(language == "") {


            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Choose a language")
                    .setMessage("Which language would you like?")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            setLanguage("English");

                        }
                    })
                    .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            setLanguage("Spanish");
                        }
                    })
                    .show();
        } else {

            textView.setText(language);

        }
    }
}
