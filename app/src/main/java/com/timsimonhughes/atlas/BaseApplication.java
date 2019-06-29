package com.timsimonhughes.atlas;

import android.app.Application;

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(NetworkConnectivityReceiver.ConnectivityReceiverListener listener) {
        NetworkConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
