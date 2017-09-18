package com.example.vinicius.jsonexampleweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;

            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream  in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1){


                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//              Log.i("Website Content", result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                String list = jsonObject.getString("list");

//                Log.i("List" , jsonObject.toString());

//                Get the results of the query
                JSONArray jsonArray = new JSONArray(list);

//               Get the first result of the query
                JSONObject firstresult = jsonArray.getJSONObject(0);

//              Get the weather of the first result
                jsonArray =  firstresult.getJSONArray("weather");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    Log.i("main", jsonPart.getString("main"));
                    Log.i("description", jsonPart.getString("description"));

                }







            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        task.execute("http://api.openweathermap.org/data/2.5/find?q=London,uk&APPID=78afdaecdb2d38cd2fb5edcbd314b5c2");
    }
}
