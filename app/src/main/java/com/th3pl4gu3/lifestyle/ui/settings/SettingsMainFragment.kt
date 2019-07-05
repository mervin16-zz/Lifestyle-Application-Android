package com.th3pl4gu3.lifestyle.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.th3pl4gu3.lifestyle.R

class SettingsMainFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_settings_main)
    }
}