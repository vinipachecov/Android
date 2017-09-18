package com.example.vinicius.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;

    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startListening();

        }
    }

    public void startListening(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

    }

    public void updateLocationInfo(Location location){


        Log.i("LocationInfo", location.toString());

        TextView latTextview = (TextView) findViewById(R.id.latitudetextView);

        TextView loTextView = (TextView) findViewById(R.id.longitudetextView);

        TextView accTextView = (TextView) findViewById(R.id.acctextView);

        TextView altTextView = (TextView) findViewById(R.id.alttextView);

        TextView addTextView = (TextView) findViewById(R.id.addtextView);

        latTextview.setText("Latitude: " + location.getLatitude());

        loTextView.setText("Longitute: " + location.getLongitude());

        altTextView.setText("Altitude: " + location.getAltitude());

        accTextView.setText("Accuracy: " + location.getAccuracy());

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            String address = "Could not find addres";

            List<Address> addressList =  geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            if(addressList != null && addressList.size() > 0) {

                address = "";

                Log.i("PlaceInfo", addressList.get(0).toString());

                if(addressList.get(0).getSubThoroughfare() != null ){

                    address += addressList.get(0).getSubThoroughfare() + " ";
                }

                if(addressList.get(0).getThoroughfare() != null ){

                    address += addressList.get(0).getThoroughfare() + "\n";

                }

                if(addressList.get(0).getLocality() != null ){

                    address += addressList.get(0).getLocality() + "\n";

                }

                if(addressList.get(0).getAdminArea() != null ){

                    address += addressList.get(0).getAdminArea() + "\n";

                }

                if(addressList.get(0).getCountryName() != null ){

                    address += addressList.get(0).getCountryName() + "\n";

                }

            }

            addTextView.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updateLocationInfo(location);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

//        Check compatibility and ask permission
        if (Build.VERSION.SDK_INT < 23){

            startListening();

        } else {

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            } else {
//                we have permission

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if( location != null) {

                    updateLocationInfo(location);

                }


            }

        }

    }
}
