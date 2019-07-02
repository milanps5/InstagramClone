package com.milanps.instagramclone;

import android.app.Application;

import com.parse.Parse;

/**
 * Milan Pop Stefanija
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JyUS9n6KziXqSBM0lH5wCiVmqfH9qj8vKfzXG9td")
                .clientKey("SENwSdc5hYkbGFAa7eDH6k8v4sAG74GkueqsMxtz")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
