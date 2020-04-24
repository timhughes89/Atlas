package com.timsimonhughes.atlas.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


import com.timsimonhughes.atlas.receivers.NetworkConnectivityReceiver
import com.google.android.material.snackbar.Snackbar
import com.timsimonhughes.atlas.AtlasApp
import com.timsimonhughes.atlas.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NetworkConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Manually check internet connection
//        checkConnection()

        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

        bottomNavigationView.setupWithNavController(
            Navigation.findNavController(
                this, R.id.nav_host_fragment
            )
        )
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
