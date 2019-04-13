package com.timsimonhughes.atlas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timsimonhughes.atlas.R

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_settings, container, false)

//        val fragmentManager = fragmentManager
//        fragmentManager
//                ?.beginTransaction()
//                ?.add(R.id.fragment_preferences, SettingsPreferenceFragment())
//                ?.commit()


        return view
    }
}