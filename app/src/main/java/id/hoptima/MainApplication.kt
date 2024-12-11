package id.hoptima

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import id.hoptima.ui.settings.SettingsFragment
import java.util.Locale

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        restorePreferences()
    }

    private fun restorePreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val languageCode = sharedPreferences.getString(SettingsFragment.KEY_LANGUAGE, "en") ?: "en"
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(languageCode))
        )

        val isDarkMode = sharedPreferences.getBoolean(SettingsFragment.KEY_DARK_MODE, false)
        val mode =
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}