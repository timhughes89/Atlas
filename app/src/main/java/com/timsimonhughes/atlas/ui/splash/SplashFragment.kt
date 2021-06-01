package com.timsimonhughes.atlas.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.timsimonhughes.atlas.R


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)

//        navController = findNavController()

        Handler().postDelayed( {
            handleNavigation()
        }, 3000)
    }

    private fun handleNavigation() {

        findNavController().navigate(R.id.action_navigation_splash_to_navigation_news)

//        if (sharedPreferences.contains(Constants.FIRST_RUN)) {
//            navController.navigate(R.id.action_navigation_splash_to_navigation_news)
//        } else {
//            sharedPreferences.edit().putString(SHARED_PREFS_KEY, Constants.FIRST_RUN).apply()
//            navController.navigate(R.id.action_navigation_splash_to_navigation_onboarding)
//        }
    }

    companion object {
        const val SHARED_PREFS_KEY = "SHARED_PREFS"
        const val SHARED_PREFS_FIRST_RUN = "first_run"
    }
}