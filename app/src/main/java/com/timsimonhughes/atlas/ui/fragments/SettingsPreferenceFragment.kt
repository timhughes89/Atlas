package com.timsimonhughes.atlas.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingsPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.timsimonhughes.atlas.R.xml.pref_general, rootKey)
    }

    companion object {
        val settingsPreferenceFragment = SettingsPreferenceFragment
    }
}