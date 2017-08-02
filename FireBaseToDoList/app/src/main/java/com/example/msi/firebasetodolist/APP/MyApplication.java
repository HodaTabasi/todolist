package com.example.msi.firebasetodolist.APP;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by M.S.I on 8/2/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
