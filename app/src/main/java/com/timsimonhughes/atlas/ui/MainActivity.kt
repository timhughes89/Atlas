package com.timsimonhughes.atlas.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.timsimonhughes.atlas.AtlasApp
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.receivers.NetworkConnectivityReceiver
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NetworkConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_splash,
                R.id.navigation_onboarding,
                R.id.navigation_news_detail,
                R.id.navigation_discover_detail,
                R.id.navigation_POTD_detail,
                R.id.navigation_settings,
                R.id.launchesDetailFragment -> showHideNavigation(false)
                else -> showHideNavigation(true)
            }
        }

        bottomNavigationView.setupWithNavController(
            Navigation.findNavController(
                this, R.id.nav_host_fragment
            )
        )
    }

    private fun showHideNavigation(show: Boolean) {
        if (!show) {
            bottomNavigationView.visibility = View.GONE
        } else {
            bottomNavigationView.visibility = View.VISIBLE
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showSnack(isConnected)
    }

    private fun showSnack(isConnected: Boolean) {
        val message: String = if (isConnected) {
            "Network connected"
        } else {
            "No internet connection"
        }

        val snackBar = Snackbar.make(container, message, Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    override fun onResume() {
        super.onResume()
        AtlasApp().getInstance().setConnectivityListener(this)
    }

}
