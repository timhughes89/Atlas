package com.timsimonhughes.atlas.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.timsimonhughes.atlas.Constants
import com.timsimonhughes.atlas.R

import com.timsimonhughes.atlas.ui.fragments.MainFragment
import com.timsimonhughes.atlas.ui.fragments.SplashFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val packageName = packageName

        val fragmentManager = supportFragmentManager
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        if (sharedPreferences.contains(Constants.FIRST_RUN)) {
            fragmentManager?.beginTransaction()?.add(R.id.container, MainFragment())?.commit()
        } else {
            sharedPreferences.edit().putString(Constants.FIRST_RUN, "first_run").apply()
            fragmentManager?.beginTransaction()?.add(R.id.container, SplashFragment())?.commit()
        }

        // Sets default values only once, first time this is called. The third
        // argument is a boolean that indicates whether the default values
        // should be set more than once. When false, the system sets the default
        // values only the first time it is called.
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false)
        PreferenceManager.setDefaultValues(this, R.xml.pref_account, false)

        // Read settings from the shared preferences and display a toast.
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val marketPref = sharedPref.getString("sync_frequency", "-1")
//        displayToast(marketPref)
    }

    private fun displayToast(message: String) {
        Toast.makeText(applicationContext, message,
                Toast.LENGTH_SHORT).show()
    }
}
