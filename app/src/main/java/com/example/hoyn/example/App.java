package com.example.hoyn.example;

import android.app.Application;
import android.content.Context;

/**
 * Created by hoyn on 2020/7/1.
 */

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
