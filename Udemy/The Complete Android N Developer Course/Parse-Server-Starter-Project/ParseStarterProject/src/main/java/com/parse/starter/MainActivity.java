/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {


    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

// send data
//    ParseObject score = new ParseObject("Score");
////    key is the name of the variable and its value
//    score.put("username", "rob");
//    score.put("score", 86);
//
//
//    score.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//        if (e == null) {
//
//          Log.i("SaveinBackgroun", "Successfull");
//        }else {
//
//          Log.i("SaveinBackgroun", "Failed, error: " + e.toString());
//
//        }
//      }
//    });
//
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//    query.getInBackground("vNzB6Up0c3", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject object, ParseException e) {
//
//        if ( e == null && object != null){
//
//          object.put("score", 400);
//          object.saveInBackground();
//
//          Log.i("ObjectValue", object.getString("username"));
//          Log.i("ObjectValue", Integer.toString(object.getInt("score")));
//        }
//
//      }
//    });
//
// Creating tweet class
//    ParseObject tweet = new ParseObject("Tweet");
//
//    tweet.put("username", "vinicius");
//    tweet.put("tweet", "Hello WorldWeb");
//
//    tweet.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//
//        if ( e == null) {
//          Log.i("Tweet", "Sucessfull");
//        } else {
//          Log.i("Tweet", e.toString());
//        }
//
//      }
//    });
//
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
//
//    query.getInBackground("vqamtqHF3d", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject object, ParseException e) {
//        if ( e == null && object != null) {
//
//          Log.i("Tweet" , "sucess!!");
//
//          object.put("tweet", "new value for the tweet guys");
//          object.saveInBackground();
//
//        }else {
//
//          Log.i("Error on Tweet" , e.toString());
//        }
//      }
//    });
//
//    Advanced Queries *******************
//
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//    search criteria
//
//    query.whereEqualTo("username","tom");
//
//    query.setLimit(1);
//
//    query.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> objects, ParseException e) {
//        if ( e == null ){
//
//          Log.i("findinBackground", "retrieved" + objects.size() + "objects");
//
//          if(objects.size() > 0){
//
//            for (ParseObject object : objects){
//
//              Log.i("findinbackgrounresults", Integer.toString(object.getInt("score")));
//            }
//          }
//        }
//      }
//    });
//
//    Every user with more than 200 points gets 50 extra
//
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//
//    query.whereGreaterThan("score", 200);
//
//    query.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> objects, ParseException e) {
//
//        if( e == null && objects != null){
//
//          for (ParseObject object : objects){
//
//            object.put("score", object.getInt("score") + 50);
//            object.saveInBackground();
//          }
//
//
//        }
//
//      }
//    });
//
// Parse user exampple
//    ParseUser user = new ParseUser();
//
//    user.setUsername("vinicius");
//
//    user.setPassword("123456");
//
//    user.signUpInBackground(new SignUpCallback() {
//      @Override
//      public void done(ParseException e) {
//        if (e == null){
//
//          Log.i("Sign up", "Sucessful");
//        } else {
//
//          Log.i("Sign up", "error");
//        }
//      }
//    });
//
// Login example
//    ParseUser.logInInBackground("viicius", "1qwa", new LogInCallback() {
//      @Override
//      public void done(ParseUser user, ParseException e) {
//
//        if (e == null && user != null){
//
//          Log.i("Login", "sucessful");
//        } else {
//
//          Log.i("Login", "Failed: " + e.toString());
//        }
//      }
//    });
//
// check if there is someone logged in
//
//    logout user
    ParseUser.logOut();

    if(ParseUser.getCurrentUser() != null) {

      Log.i("Currentuser", "User logged: " + ParseUser.getCurrentUser().getUsername());

    }else {

      Log.i("Currentuser", "User not logged in ");

    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}
