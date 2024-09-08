package com.example.goldsecure.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.goldsecure.R
import androidx.appcompat.app.AppCompatDelegate

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Загружаем настройки из XML файла
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // Получаем переключатель темы
        val themeSwitch = findPreference<SwitchPreferenceCompat>("theme_preference")

        themeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            val isDarkMode = newValue as Boolean
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }
    }
}
