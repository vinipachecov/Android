package com.example.root.numbershapes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void testNumber(View view){

        EditText usersNumber = (EditText) findViewById(R.id.NumberEditText);
        String message = "";

        if(usersNumber.getText().toString().isEmpty()) {

            message = "Please enter a number";
        }
        else{
            Number myNumber = new Number();

            myNumber.number = Integer.parseInt(usersNumber.getText().toString());



            if(myNumber.isSquare()){

                if(myNumber.isTriangular()){
                    message = myNumber.number + " is both triangular and square!";
                } else {
                    message = myNumber.number + " is  square but not triangular.";
                }
            } else {
                if(myNumber.isTriangular()){
                    message = myNumber.number + " is triangular but not  square!";
                } else {
                    message = myNumber.number + " is  neither square nor triangular.";
                }
            }
            //Log.d("TESTS", "testNumber: " + usersNumber.getText().toString());
        }

        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
