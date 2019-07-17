package com.th3pl4gu3.lifestyle.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.ui.utils.toast


class SettingsMainFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings_main, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferenceBackupLocal =
            findPreference<Preference>(getString(R.string.ValueKey_forSettingsPreference_Backup_Local))
        val preferenceBackupCloud =
            findPreference<Preference>(getString(R.string.ValueKey_forSettingsPreference_Backup_Cloud))
        val preferenceRestoreLocal =
            findPreference<Preference>(getString(R.string.ValueKey_forSettingsPreference_Restore_Local))
        val preferenceRestoreCloud =
            findPreference<Preference>(getString(R.string.ValueKey_forSettingsPreference_Restore_Cloud))
        val preferenceAbout =
            findPreference<Preference>(getString(R.string.ValueKey_forSettingsPreference_About_AboutUs))

        preferenceBackupLocal?.onPreferenceClickListener = this
        preferenceBackupCloud?.onPreferenceClickListener = this
        preferenceRestoreLocal?.onPreferenceClickListener = this
        preferenceRestoreCloud?.onPreferenceClickListener = this
        preferenceAbout?.onPreferenceClickListener = this
    }

    override fun onResume() {
        super.onResume()

        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()

        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        requireContext().toast("Preference Changed")
    }

    override fun onPreferenceClick(preference: Preference): Boolean {

        return when (preference.key) {
            getString(R.string.ValueKey_forSettingsPreference_Backup_Local) -> {
                try {

                    (requireActivity() as SettingsActivity).viewModel.backupLocally()

                } catch (ex: Exception) {
                    requireContext().toast(ex.message.toString())
                }
                true
            }
            getString(R.string.ValueKey_forSettingsPreference_Backup_Cloud) -> {
                requireContext().toast("Creation of Cloud Backup has not been implemented yet.")
                true
            }
            getString(R.string.ValueKey_forSettingsPreference_Restore_Local) -> {
                try {
                    (requireActivity() as SettingsActivity).viewModel.restoreLocally()

                } catch (ex: Exception) {
                    requireContext().toast(ex.message.toString())
                }

                true
            }
            getString(R.string.ValueKey_forSettingsPreference_Restore_Cloud) -> {
                requireContext().toast("Cloud Restoration has not been implemented yet.")
                true
            }
            getString(R.string.ValueKey_forSettingsPreference_About_AboutUs) -> {
                requireContext().toast("About Us has not been implemented yet.")
                true
            }

            else -> {
                false
            }
        }
    }
}