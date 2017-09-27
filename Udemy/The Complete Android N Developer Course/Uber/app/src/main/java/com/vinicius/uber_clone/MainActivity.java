package com.vinicius.uber_clone;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {



    public void redirectActivity(){

        //check if a user is a driver or rider

        if(ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")) {

            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);

            startActivity(intent);

        } else {

            Intent intent = new Intent(getApplicationContext(), ViewRequestsActivity.class);

            startActivity(intent);
        }

    }

    public void getStarted(View view){

        Switch usertySwitch = (Switch) findViewById(R.id.userTypeSwitch);

        String userType = "rider";

        if(usertySwitch.isChecked()){

            userType = "driver";
        }

        ParseUser.getCurrentUser().put("riderOrDriver",userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                redirectActivity();

            }
        });


        Log.i("Info", "redirecting as " +  userType);

        redirectActivity();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        if(ParseUser.getCurrentUser() == null){

            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e == null){

                        Log.i("Info", "Anonymous successful");



                    }else {

                        Log.i("Info", "Anonymous failed");
                    }

                }
            });
        } else {

            if (ParseUser.getCurrentUser().get("riderOrDriver") != null){

                Log.i("Info", "redirecting as " +  ParseUser.getCurrentUser().get("riderOrDriver"));

                redirectActivity();

            }
        }



        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
