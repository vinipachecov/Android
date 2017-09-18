package com.example.vinicius.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {


    EditText cityName;

    TextView resultTextView;

    DownloadTask task;





    public void findTheWeather(View view) {

        Log.i("CityName", cityName.getText().toString());

        String apiID = "&APPID=78afdaecdb2d38cd2fb5edcbd314b5c2";

        //        remove keyboard  after tapped-- get the input manager

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

//         hide keyboard
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);


        try {

            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");

            DownloadTask task = new DownloadTask();

            task.execute("http://api.openweathermap.org/data/2.5/find?q=" + encodedCityName + apiID);


        } catch (UnsupportedEncodingException e) {
            Toast.makeText(getApplicationContext(), "Could not find Weather", Toast.LENGTH_LONG);
            e.printStackTrace();
        }




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText)findViewById(R.id.cityName);
        resultTextView = (TextView)findViewById(R.id.resultTextView);



    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;

            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1){


                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Could not find Weather", Toast.LENGTH_LONG);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//              Log.i("Website Content", result);

            try {

                String message = "";

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

                    String main = "";

                    String description = "";

                    main = jsonPart.getString("main");

                    description = jsonPart.getString("description");

                    if ( main != "" && description != ""){

                        message += main + " : " + description + "\r\n";

                    }
//                    Log.i("description", jsonPart.getString("description"));

                }



                if ( message != "") {
                    resultTextView.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(), "Could not find Weather", Toast.LENGTH_LONG);
                }



            } catch (JSONException e) {

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find Weather", Toast.LENGTH_LONG);
            }

        }
    }


}
