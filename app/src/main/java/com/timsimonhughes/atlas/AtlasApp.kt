package com.timsimonhughes.atlas

import android.app.Application
import android.content.Context
import com.timsimonhughes.atlas.receivers.NetworkConnectivityReceiver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class AtlasApp : Application() {

    override fun onCreate() {
        super.onCreate()

        atlasApplicationInstance = this
        context = applicationContext

        startKoin {
            androidContext(this@AtlasApp)
            modules(Modules.modules.value)
        }
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