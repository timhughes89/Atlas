package com.timsimonhughes.atlas.ui.controller

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.timsimonhughes.atlas.R

import com.timsimonhughes.atlas.ui.fragments.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        fragmentManager?.beginTransaction()?.add(com.timsimonhughes.atlas.R.id.container, MainFragment())?.commit()

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
        displayToast(marketPref)
    }

    private fun displayToast(message: String) {
        Toast.makeText(applicationContext, message,
                Toast.LENGTH_SHORT).show()
    }
}
