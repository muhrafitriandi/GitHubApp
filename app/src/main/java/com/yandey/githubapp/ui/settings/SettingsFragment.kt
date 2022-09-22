package com.yandey.githubapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.yandey.githubapp.R
import com.yandey.core.utils.DarkMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            newValue?.let {
                val theme = when ((it as String)) {
                    getString(R.string.pref_dark_on) -> DarkMode.ON
                    getString(R.string.pref_dark_off) -> DarkMode.OFF
                    else -> DarkMode.AUTO
                }
                updateTheme(theme.value)
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}