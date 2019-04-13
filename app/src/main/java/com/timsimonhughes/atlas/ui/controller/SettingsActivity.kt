package com.timsimonhughes.atlas.ui.controller

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.ui.fragments.SettingsPreferenceFragment


//class SettingsActivity : AppCompatPreferenceActivity() {

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frag_settings)
//        setupActionBar()
        setupToolbar()

        val fragmentManager = supportFragmentManager
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_preferences, SettingsPreferenceFragment())
                .commit()
    }

    private fun setupToolbar() {

        // Set the toolbar as support action bar
        setSupportActionBar(findViewById(R.id.toolbar_settings))
        // Toolbar Home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.app_settings)

    }

//    private fun setupActionBar() {
//        val actionBar = supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//    }
//
//    override fun onMenuItemSelected(featureId: Int, item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == android.R.id.home) {
//            if (!super.onMenuItemSelected(featureId, item)) {
//                NavUtils.navigateUpFromSameTask(this)
//            }
//            return true
//        }
//        return super.onMenuItemSelected(featureId, item)
//    }
//
//    override fun onIsMultiPane(): Boolean {
//        return isXLargeTablet(this)
//    }
//
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    override fun onBuildHeaders(target: List<Header>) {
//        loadHeadersFromResource(com.timsimonhughes.nasapotd.R.xml.pref_headers, target)
//    }
//
//     override fun isValidFragment(fragmentName: String): Boolean {
//        return (PreferenceFragment::class.java.name == fragmentName
//                || GeneralPreferenceFragment::class.java.name == fragmentName
//                || DataSyncPreferenceFragment::class.java.name == fragmentName
//                || NotificationPreferenceFragment::class.java.name == fragmentName)
//    }
//
//    ///////// GENERAL PREFERENCES /////////
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    class GeneralPreferenceFragment : PreferenceFragment() {
//        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        }
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            addPreferencesFromResource(com.timsimonhughes.nasapotd.R.xml.pref_general)
//            setHasOptionsMenu(true)
//
//            bindPreferenceSummaryToValue(findPreference("example_text"))
//            bindPreferenceSummaryToValue(findPreference("example_list"))
//        }
//
//        override fun onOptionsItemSelected(item: MenuItem): Boolean {
//            val id = item.itemId
//            if (id == android.R.id.home) {
//                startActivity(
//                        Intent(activity, SettingsActivity::class.java))
//                return true
//            }
//            return super.onOptionsItemSelected(item)
//        }
//    }
//
//    ///////// NOTIFICATION PREFERENCES /////////
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    class NotificationPreferenceFragment : PreferenceFragment() {
//        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        }
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            addPreferencesFromResource(com.timsimonhughes.nasapotd.R.xml.pref_notification)
//            setHasOptionsMenu(true)
//
//            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"))
//        }
//
//        override fun onOptionsItemSelected(item: MenuItem): Boolean {
//            val id = item.itemId
//            if (id == android.R.id.home) {
//                startActivity(
//                        Intent(activity, SettingsActivity::class.java))
//                return true
//            }
//            return super.onOptionsItemSelected(item)
//        }
//    }
//
//    ///////// DATA SYNC PREFERENCES /////////
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    class DataSyncPreferenceFragment : PreferenceFragment() {
//        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        }
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            addPreferencesFromResource(com.timsimonhughes.nasapotd.R.xml.pref_account)
//            setHasOptionsMenu(true)
//
//            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
//            // to their values. When their values change, their summaries are
//            // updated to reflect the new value, per the Android Design
//            // guidelines.
//            bindPreferenceSummaryToValue(findPreference("sync_frequency"))
//        }
//
//        override fun onOptionsItemSelected(item: MenuItem): Boolean {
//            val id = item.itemId
//            if (id == android.R.id.home) {
//                startActivity(
//                        Intent(activity, SettingsActivity::class.java))
//                return true
//            }
//            return super.onOptionsItemSelected(item)
//        }
//    }
//
//
//    ///////// STATIC METHODS /////////
//    companion object {
//
//        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
//            val stringValue = value.toString()
//
//            if (preference is ListPreference) {
//                // For list preferences, look up the correct display value in
//                // the preference's 'entries' list.
//                val listPreference = preference as ListPreference
//                val index = listPreference.findIndexOfValue(stringValue)
//
//                // Set the summary to reflect the new value.
//                preference.setSummary(
//                        if (index >= 0)
//                            listPreference.entries[index]
//                        else
//                            null)
//
//            } else if (preference is RingtonePreference) {
//                // For ringtone preferences, look up the correct display value
//                // using RingtoneManager.
//                if (TextUtils.isEmpty(stringValue)) {
//                    // Empty values correspond to 'silent' (no ringtone).
//                    preference.setSummary(com.timsimonhughes.nasapotd.R.string.pref_ringtone_silent)
//
//                } else {
//                    val ringtone = RingtoneManager.getRingtone(
//                            preference.getContext(), Uri.parse(stringValue))
//
//                    if (ringtone == null) {
//                        // Clear the summary if there was a lookup error.
//                        preference.setSummary(null)
//                    } else {
//                        // Set the summary to reflect the new ringtone display
//                        // name.
//                        val name = ringtone.getTitle(preference.getContext())
//                        preference.setSummary(name)
//                    }
//                }
//
//            } else {
//                // For all other preferences, set the summary to the value's
//                // simple string representation.
//                preference.summary = stringValue
//            }
//            true
//        }
//
//
//        private fun isXLargeTablet(context: Context): Boolean {
//            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
//        }
//
//        private fun bindPreferenceSummaryToValue(preference: Preference) {
//            // Set the listener to watch for value changes.
//            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener
//
//            // Trigger the listener immediately with the preference's
//            // current value.
//            sBindPreferenceSummaryToValueListener
//                    .onPreferenceChange(preference, PreferenceManager
//                            .getDefaultSharedPreferences(preference.context)
//                            .getString(preference.key, ""))
//        }
//    }
}
