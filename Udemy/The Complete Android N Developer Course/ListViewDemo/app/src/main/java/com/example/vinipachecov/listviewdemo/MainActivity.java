package com.example.vinipachecov.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //friend example
        final ListView listView = (ListView)findViewById(R.id.myListView);

        final ArrayList<String> myFriends = new ArrayList<String>(asList("John", "Paul", "George", "Ringo"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myFriends);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Hello " + myFriends.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        //tests
//
//        ListView mylistView = (ListView)findViewById(R.id.myListView);
//
//        final ArrayList<String> myFamily = new ArrayList<String>();
//
//        myFamily.add("Rob");
//        myFamily.add("Kristen");
//        myFamily.add("Tommy");
//        myFamily.add("Rralphie");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                myFamily
//        );
//
//        mylistView.setAdapter(arrayAdapter);
//
//        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("Person taaped: ", myFamily.get(position));
//            }
//        });
    }
}
