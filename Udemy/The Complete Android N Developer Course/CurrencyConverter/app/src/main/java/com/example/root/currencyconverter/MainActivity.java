package com.example.root.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void  convert(View view){
        EditText dollarAmountEditText = (EditText) findViewById(R.id.dollarAmountEditText);

        Log.i("amount", dollarAmountEditText.getText().toString());
        Double dollar = Double.parseDouble(dollarAmountEditText.getText().toString());


        Double poundAmount = dollar * 0.75;

        Toast.makeText(MainActivity.this, String.format("%.2f", poundAmount), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
