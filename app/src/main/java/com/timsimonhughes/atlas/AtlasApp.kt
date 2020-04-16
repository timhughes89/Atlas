package com.timsimonhughes.atlas

import android.app.Application

class AtlasApp : Application() {

    companion object {
        lateinit var atlasApplicationInstance: AtlasApp
    }

    override fun onCreate() {
        super.onCreate()
        atlasApplicationInstance = this
    }

    @Synchronized
    fun getInstance(): AtlasApp {
        return atlasApplicationInstance
    }

    fun setConnectivityListener(listener: NetworkConnectivityReceiver.ConnectivityReceiverListener) {
        NetworkConnectivityReceiver.connectivityReceiverListener = listener
    }
}