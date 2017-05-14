package com.example.vinipachecov.timestables;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public void generateTimesTable(int timesTable){

        ListView timesTableListView = (ListView)findViewById(R.id.timesTableListView);

        ArrayList<String> timesTableContent = new ArrayList<String>();

        //populate our array
        for (int i = 1 ; i <= 10 ; i ++){
            timesTableContent.add(Integer.toString(i * timesTable));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timesTableContent);

        timesTableListView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar timetablesSeekBar = (SeekBar)findViewById(R.id.timesTablesseekBar);



        timetablesSeekBar.setMax(20);
        //starter possition of the seekbar
        timetablesSeekBar.setProgress(10);

        timetablesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //settting miinimum value on the seekbar

                int min = 1;
                int timesTable;

                if(progress < 1){
                    timesTable = min;
                    timetablesSeekBar.setProgress(min);
                }else {
                    timesTable = progress;
                }

                generateTimesTable(timesTable);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateTimesTable(10);


    }
}
