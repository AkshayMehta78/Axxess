package app.axxess;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class Axxess extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
