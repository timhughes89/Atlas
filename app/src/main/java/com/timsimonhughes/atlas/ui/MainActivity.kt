package com.timsimonhughes.atlas.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timsimonhughes.atlas.receivers.NetworkConnectivityReceiver
import com.timsimonhughes.atlas.ui.splash.SplashFragment
import com.google.android.material.snackbar.Snackbar
import com.timsimonhughes.atlas.AtlasApp
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NetworkConnectivityReceiver.ConnectivityReceiverListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.timsimonhughes.atlas.R.layout.activity_main)

        // Manually check internet connection
        checkConnection()

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(com.timsimonhughes.atlas.R.id.container, SplashFragment()).commit()
    }

    private fun checkConnection() {
        val isConnected = NetworkConnectivityReceiver().isConnected()
        showSnack(isConnected)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showSnack(isConnected)
    }

    private fun showSnack(isConnected: Boolean) {
        val message: String

        if (isConnected) {
            message = "Network connected"
        } else {
            message = "No internet connection"
        }

        val snackbar = Snackbar.make(container, message, Snackbar.LENGTH_LONG)
        snackbar.show()

    }

    private fun displayToast(message: String) {
        Toast.makeText(applicationContext, message,
                Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        AtlasApp().getInstance().setConnectivityListener(this)
    }
}
