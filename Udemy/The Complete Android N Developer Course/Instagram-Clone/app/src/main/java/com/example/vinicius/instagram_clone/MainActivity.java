package com.example.vinicius.instagram_clone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.Keyboard;
import android.provider.MediaStore;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    EditText usernameEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeSignUpModeTextView = (TextView)findViewById(R.id.changeSignUpModetextView);

        changeSignUpModeTextView.setOnClickListener(this);

        usernameEditText = (EditText) findViewById(R.id.usernameeditText);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.backgroundConstraintLayout);

        ImageView imageView = (ImageView) findViewById(R.id.logoimageView);

        constraintLayout.setOnClickListener(this);

        imageView.setOnClickListener(this);

        passwordEditText = (EditText) findViewById(R.id.passwordeditText);

        passwordEditText.setOnKeyListener(this);

        if (ParseUser.getCurrentUser() != null){

            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    public void showUserList() {

        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);

        startActivity(intent);
    }

    EditText passwordEditText;
    Button signUpButton;

    TextView changeSignUpModeTextView;

    Boolean signUpModeActive = true;

    public boolean onKey(View view, int i , KeyEvent keyEvent){

        if (i ==    KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

            signUp(view);

            Log.i("Click", "click Enter");
        }
        return false;
    }


    @Override
    public void onClick(View view) {

        if( view.getId() == R.id.changeSignUpModetextView) {

            Log.i("Appinfo", "change signup mode");


            if (signUpModeActive) {

                signUpButton = (Button) findViewById(R.id.signUpsButton);

                signUpModeActive = false;

                signUpButton.setText("Login");

                changeSignUpModeTextView.setText("Or, SignUp");
            } else {

                signUpButton = (Button) findViewById(R.id.signUpsButton);

                signUpModeActive = true;

                signUpButton.setText("SignUp");

                changeSignUpModeTextView.setText("Or, Login");

            }
        } else if(view.getId() == R.id.backgroundConstraintLayout || view.getId() == R.id.logoimageView){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }

    }

    public void signUp(View view) {



        if(usernameEditText.getText().toString().matches("")  ||  passwordEditText.getText().toString().matches("") ){

            Toast.makeText(this, "A username and a password are required", Toast.LENGTH_SHORT).show();
        } else {

            if(signUpModeActive) {


                ParseUser user = new ParseUser();


                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Log.i("Signup", "sucessful");

                            showUserList();

                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {

                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if ( e == null){

                            Log.i("Login", "Login Sucessful");

                            showUserList();

                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }

        }
    }



}
