package com.example.vinicius.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//Create a table in a database

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        try {
//
////            open the database if already exists or create it
//            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
//
//            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR(100), age INT(3))");
//
//            sqLiteDatabase.execSQL("INSERT INTO users (name, age) VALUES('ROB', 24)");
//
//            sqLiteDatabase.execSQL("INSERT INTO users (name, age) VALUES('John', 23)");
//
//            sqLiteDatabase.execSQL("INSERT INTO users (name, age) VALUES('Katy', 30)");
//
////            allow to loop through results of a query
//            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM users", null);
//
//
//            int nameIndex = c.getColumnIndex("name");
//            int ageIndex = c.getColumnIndex("age");
//
//            c.moveToFirst();
//
//            while( c != null) {
//
//                Log.i("name", c.getString(nameIndex));
//                Log.i("AGE", Integer.toString(c.getInt(ageIndex)));
//
//                c.moveToNext();
//            }
//
//        }
//        catch (Exception e){
//
//            e.printStackTrace();
//        }
//    }

        try {
            SQLiteDatabase eventDB = this.openOrCreateDatabase("Events", MODE_PRIVATE, null);

            eventDB.execSQL("CREATE TABLE IF NOT EXISTS events (eventname VARCHAR, year INT(4))");

            eventDB.execSQL("INSERT INTO events (eventname, year) VALUES('End of WW2', 1945)");

            eventDB.execSQL("INSERT INTO events (eventname, year) VALUES('End of WW1', 1918)");

            eventDB.execSQL("INSERT INTO events (eventname, year) VALUES('Wham split up', 1986)");

            Cursor c = eventDB.rawQuery("SELECT * FROM events", null);

            int eventIndex = c.getColumnIndex("eventname");

            int yearIndex = c.getColumnIndex("year");

            c.moveToFirst();

            while ( c != null ){

                Log.i("EventName" , c.getString(eventIndex));
                Log.i("Year", Integer.toString(c.getInt(yearIndex)));

                c.moveToNext();

            }

        }catch (Exception e){

        }
        }

}
