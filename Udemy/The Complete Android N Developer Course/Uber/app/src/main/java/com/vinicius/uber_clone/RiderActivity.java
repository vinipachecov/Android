package com.vinicius.uber_clone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static com.parse.ParseQuery.getQuery;

public class RiderActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    Button uberbutton;

    TextView infoTextView;


    LocationManager locationManager;

    LocationListener locationListener;

    Boolean requestActive = false;

    Boolean driverActive = false;

    android.os.Handler handler = new android.os.Handler();


    public void teste (){
        final ParseQuery<ParseObject> query = getQuery("Request");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereExists("driverUsername");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){

                    if (objects.size() > 0){

                        ParseObject parseObject = objects.get(0);

                        final ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("username", parseObject.getString("driverUsername"));
                        query.findInBackground(new FindCallback<ParseUser>() {
                            @Override
                            public void done(List<ParseUser> objects, ParseException e) {
                                if (e == null && objects.size() >0){

                                    ParseUser driver = objects.get(0);

//                                    Log.i("drivername", driver.get("Location").toString());
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    public void  checkForUpdates(){


        final ParseQuery<ParseObject> query = getQuery("Request");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereExists("driverUsername");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if ( e == null && objects.size() > 0) {

                    driverActive = true;

                    final ParseQuery<ParseUser> query1 = ParseUser.getQuery();

                 //   Log.i("driveruser", objects.get(0).getString("driverUsername"));

                    query1.whereEqualTo("username", objects.get(0).getString("driverUsername"));

                    query1.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {

                            if (e == null && objects.size() > 0){
//                                get the driver location from the location geopoint
//                                Log.i("driverlocation","getting it");

                                ParseGeoPoint driverLocation = objects.get(0).getParseGeoPoint("Location");

                                Log.i("driverlatitude", Double.toString(driverLocation.getLatitude()));
                                Log.i("driverlongitude", Double.toString(driverLocation.getLongitude()));
//
                                if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(RiderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                                    Log.i("driverlocation","getting it2");

//
                                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
                                    Location lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//
                                    if (lastknownlocation != null) {

//                                        Log.i("driverlocation","getting it3");
                                        ParseGeoPoint userLocation = new ParseGeoPoint(lastknownlocation.getLatitude(), lastknownlocation.getLongitude());

//                                        compare user and driver location

                                        Double distanceInMiles = driverLocation.distanceInMilesTo(userLocation);

                                        Double distanceOneDP = (double) Math.round(distanceInMiles * 10) / 10;

                                            if (distanceInMiles < 0.01) {

//                                            very small distance

                                                infoTextView.setText("Your driver is here!");
                                                final ParseQuery<ParseObject> query = getQuery("Request");
                                                query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                                                query.whereExists("driverUsername");

                                                query.findInBackground(new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> objects, ParseException e) {

                                                        if (e == null && objects.size() > 0) {
                                                            try {
                                                                objects.get(0).delete();
                                                            } catch (ParseException e1) {
                                                                e1.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                });


                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

    //                                                    RIDER ARRIVED

                                                        uberbutton.setVisibility(View.VISIBLE);
                                                        uberbutton.setText("Call an uber");


                                                      requestActive = false;
                                                     driverActive = false;
                                                 }
                                              }, 2000);

                                         } else {


//                                            Log.i("entrou" , "aqui");
                                        infoTextView.setText("Your driver " + distanceOneDP.toString() + " miles away");

                                        LatLng driverLocationLatLng = new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude());

                                        LatLng requestLocationLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

                                        ArrayList<Marker> markers = new ArrayList<>();

                                        mMap.clear();

                                        markers.add(mMap.addMarker(new MarkerOptions().position(driverLocationLatLng).title("Driver Location")));
                                        markers.add(mMap.addMarker(new MarkerOptions().position(requestLocationLatLng).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));

                                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                        for (Marker marker : markers) {
                                            builder.include(marker.getPosition());
                                        }

                                        LatLngBounds bounds = builder.build();

                                        int padding = 30;

                                        //Log.i("latlngbounds", bounds.toString());

                                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                                        mMap.animateCamera(cameraUpdate);

                                        uberbutton.setVisibility(View.INVISIBLE);

                                    }

                                }
                            }
                        }
                    }
                });

                handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        checkForUpdates();
                       teste();
                  }
                }, 2000);

                uberbutton.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    public void logout(View view) {
        ParseUser.logOut();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

    public  void callUber(View view){

        Log.i("Info", "call Uber");

        if (requestActive){

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");

            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if ( e == null ) {

                        if (objects.size() > 0) {


                            for (ParseObject object : objects) {

                                object.deleteInBackground();
                            }

                            requestActive = false;

                            uberbutton.setText("Call an Uber");
                        }
                    }

                }
            });

        } else {

            Log.i("caiu no else", "caiu no else");
            if (ContextCompat.checkSelfPermission(RiderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Log.i("caiu no else", "tem permissão");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastknownlocation != null) {

                    Log.i("caiu no else", "ultima localização não é null");

                    ParseObject request = new ParseObject("Request");

                    request.put("username", ParseUser.getCurrentUser().getUsername());

                    ParseGeoPoint parseGeoPoint = new ParseGeoPoint(lastknownlocation.getLatitude(), lastknownlocation.getLongitude());

                    request.put("location", parseGeoPoint);

                    request.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Log.i("New request" , "True");

                                uberbutton.setText("Cancel Uber");
                                requestActive = true;
                              //                                get updates
                               checkForUpdates();
                            }

                        }
                    });
                    Log.i("caiu no else", "terminou de salvar");

                } else {

                    Toast.makeText(this, "Could not ind location. Please try again later", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        Check the result of the permission request

        if (requestCode == 1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    Location lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    updateMap(lastknownlocation);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        uberbutton = (Button)findViewById(R.id.callUberbutton);

        infoTextView = (TextView)findViewById(R.id.infoTextView);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");

        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if ( e == null && objects.size() > 0){

                    Log.i("request activated" , "True");
                    requestActive = true;

                    uberbutton.setText("Cancel Uber");

                    checkForUpdates();
                }

            }
        });
    }


    public void updateMap(Location location){

        //if (driverActive != false) {


            mMap.clear();

            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));

            mMap.addMarker(new MarkerOptions().position(userLocation).title("Your location"));

        //}


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                update the map with the users location

                updateMap(location);
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

//        permissions

        if(Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else {

//            CHECK IF WE DON'T HAVE PERMISSION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastknownlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastknownlocation != null ) {

                    updateMap(lastknownlocation);
                }
            }

        }

    }
}
