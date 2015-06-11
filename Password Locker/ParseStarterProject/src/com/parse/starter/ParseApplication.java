package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
      super.onCreate();

      ParseCrashReporting.enable(this);
      Parse.enableLocalDatastore(this);

      Parse.initialize(this, "AmCiL68cfwigeoO1kYz6qwTBmnN3F2vNV0N2A2if", "rcXEYLaFHknGIr07NsVV1AyE5VvGtYKYj6K18XaX");

      ParseUser.enableAutomaticUser();
      ParseACL defaultACL = new ParseACL();

      ParseACL.setDefaultACL(defaultACL, true);
  }
}
