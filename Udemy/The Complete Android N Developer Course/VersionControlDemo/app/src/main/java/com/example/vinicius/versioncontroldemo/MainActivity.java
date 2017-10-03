 package com.example.vinicius.versioncontroldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "My first IDE version control app", Toast.LENGTH_SHORT).show();

        // Facebook testing!

//        Not working very well
    }
}
