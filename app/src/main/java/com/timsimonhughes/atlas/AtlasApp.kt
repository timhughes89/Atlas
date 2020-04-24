package com.timsimonhughes.atlas

import android.app.Application
import android.content.Context
import com.timsimonhughes.atlas.receivers.NetworkConnectivityReceiver

class AtlasApp : Application() {

    override fun onCreate() {
        super.onCreate()
        atlasApplicationInstance = this
        context = applicationContext

    }

    @Synchronized
    fun getInstance(): AtlasApp {
        return atlasApplicationInstance
    }

    fun setConnectivityListener(listener: NetworkConnectivityReceiver.ConnectivityReceiverListener) {
        NetworkConnectivityReceiver.connectivityReceiverListener = listener
    }

    companion object {
        lateinit var atlasApplicationInstance: AtlasApp
        var context: Context? = null
    }

}