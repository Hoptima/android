package id.hoptima.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import id.hoptima.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}