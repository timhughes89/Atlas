package com.timsimonhughes.atlas.ui.activities

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timsimonhughes.atlas.NetworkConnectivityReceiver
import com.timsimonhughes.atlas.ui.fragments.SplashFragment
import com.google.android.material.snackbar.Snackbar
import com.timsimonhughes.atlas.BaseApplication
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NetworkConnectivityReceiver.ConnectivityReceiverListener {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.timsimonhughes.atlas.R.layout.activity_main)

        // Manually check internet connection
        checkConnection()

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(com.timsimonhughes.atlas.R.id.container, SplashFragment()).commit()

        // Sets default values only once, first time this is called. The third
        // argument is a boolean that indicates whether the default values
        // should be set more than once. When false, the system sets the default
        // values only the first time it is called.
        PreferenceManager.setDefaultValues(this, com.timsimonhughes.atlas.R.xml.pref_general, false)
        PreferenceManager.setDefaultValues(this, com.timsimonhughes.atlas.R.xml.pref_notification, false)
        PreferenceManager.setDefaultValues(this, com.timsimonhughes.atlas.R.xml.pref_account, false)

        // Read settings from the shared preferences and display a toast.
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val marketPref = sharedPref.getString("sync_frequency", "-1")
//        displayToast(marketPref)
    }

    private fun checkConnection() {
        val isConnected = NetworkConnectivityReceiver.isConnected()
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
        BaseApplication.getInstance().setConnectivityListener(this)
    }
}
