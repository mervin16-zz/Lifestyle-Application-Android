package com.th3pl4gu3.lifestyle.ui.settings

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.ui.utils.*


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

                performBackupPreview()

                true
            }
            getString(R.string.ValueKey_forSettingsPreference_Backup_Cloud) -> {
                requireContext().toast("Creation of Cloud Backup has not been implemented yet.")
                true
            }
            getString(R.string.ValueKey_forSettingsPreference_Restore_Local) -> {

                performRestorePreview()

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

    private fun performBackupPreview() {
        // Check if the Write permission has been granted
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already available, perform backup
            (requireActivity() as SettingsActivity).performBackup()
        } else {
            // Permission is missing and must be requested.
            requestStorageWritePermissions(CODE_PERMISSIONS_STORAGE_EXTERNAL_WRITE_BACKUP)
        }
    }

    private fun performRestorePreview() {
        // Check if the Write permission has been granted
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already available, perform restore
            (requireActivity() as SettingsActivity).performRestore()
        } else {
            // Permission is missing and must be requested.
            requestStorageWritePermissions(CODE_PERMISSIONS_STORAGE_EXTERNAL_WRITE_RESTORE)
        }
    }

    private fun requestStorageWritePermissions(requestCode: Int) {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )

        } else {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.Dialog_fromSettingsMainFragment_BackupRestore_Permissions_Write_Title))
                .setMessage(getString(R.string.Dialog_fromSettingsMainFragment_BackupRestore_Permissions_Write_Content))
                .setPositiveButton(getString(R.string.Dialog_fromSettingsMainFragment_BackupRestore_Permissions_Write_PositiveButton)) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts(VALUE_URI_SCHEME_PACKAGE_NAME, requireActivity().packageName, null)
                    startActivityForResult(intent, CODE_INTENT_APPLICATION_DETAILS_SETTINGS)
                }
                .setNegativeButton(getString(R.string.Dialog_fromSettingsMainFragment_BackupRestore_Permissions_Write_NegativeButton)) { _, _ ->
                    requireContext().toast(getString(R.string.Message_Info_fromSettingsMainFragment_Permissions_Denied))
                }
                .show()
        }


    }
}