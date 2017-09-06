package com.example.vinipachecov.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

//    https://www.ecowebhosting.co.uk/


    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;

//            Like a browser
            HttpURLConnection urlConnection = null;

            try {
//                convert the string we passed in the doing backgroun method
                url = new URL(urls[0]);


//                effectively created a connection to the browser
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

//                keep track of the location of the url data
                int data = reader.read();

                while(data != -1){

//                    cast data to char since data is an integer;
                    char current = (char) data;

//                    add the current character to the result
                    result += current;

//                    move data to the next character
                    data = reader.read();
                }

                return result;

            }catch (Exception e){
                e.printStackTrace();

                return "Failed";
            }

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {

            result = task.execute("https://www.ecowebhosting.co.uk/").get();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();
        }

        Log.i("Contents of URL", result);
    }
}
