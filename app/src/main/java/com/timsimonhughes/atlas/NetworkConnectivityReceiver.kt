package com.timsimonhughes.atlas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkConnectivityReceiver : BroadcastReceiver() {

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo?
        activeNetwork = connectivityManager.activeNetworkInfo

        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        connectivityReceiverListener?.onNetworkConnectionChanged(isConnected)
    }

    fun isConnected() : Boolean {
        val connectivityManager = AtlasApp().getInstance().applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo?
        activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}