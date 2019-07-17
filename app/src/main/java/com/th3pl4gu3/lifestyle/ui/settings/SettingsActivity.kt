package com.th3pl4gu3.lifestyle.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.database.LifestyleDatabase
import com.th3pl4gu3.lifestyle.databinding.ActivitySettingsBinding
import com.th3pl4gu3.lifestyle.ui.utils.NotificationUtils
import com.th3pl4gu3.lifestyle.ui.utils.toast

class SettingsActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySettingsBinding
    lateinit var viewModel: SettingsViewModel
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        //Get the activity's application
        val application = requireNotNull(this).application

        //Fetch the database
        val dataSource = LifestyleDatabase.getInstance(application)

        //Instantiate the view model of this activity
        viewModel = ViewModelProviders.of(this, SettingsViewModelFactory(dataSource, application))
            .get(SettingsViewModel::class.java)

        //Instantiate the lifecycle owner
        _binding.lifecycleOwner = this

        viewModel.showBackupProgressNotification.observe(this, Observer { isVisible ->

            with(NotificationManagerCompat.from(this)) {

                val builder: NotificationCompat.Builder = NotificationUtils.showBackupProgressNotification(
                    applicationContext,
                    getString(R.string.Notification_Backup_Title),
                    getString(R.string.Notification_Backup_Text)
                )

                if (isVisible) {
                    builder.setProgress(0, 0, true)
                    notify(NotificationUtils.BACKUP_AND_RESTORE_NOTIFICATION_ID, builder.build())
                } else {
                    NotificationUtils.progressComplete(builder, getString(R.string.Notification_Backup_TextComplete))
                    notify(NotificationUtils.BACKUP_AND_RESTORE_NOTIFICATION_ID, builder.build())
                    applicationContext.toast(getString(R.string.Message_Success_fromSettings_BackupComplete))
                }
            }
        })

        viewModel.showRestoreProgressNotification.observe(this, Observer { isVisible ->

            with(NotificationManagerCompat.from(this)) {

                val builder: NotificationCompat.Builder = NotificationUtils.showBackupProgressNotification(
                    applicationContext,
                    getString(R.string.Notification_Restore_Title),
                    getString(R.string.Notification_Restore_Text)
                )

                if (isVisible) {
                    builder.setProgress(0, 0, true)
                    notify(NotificationUtils.BACKUP_AND_RESTORE_NOTIFICATION_ID, builder.build())
                } else {
                    NotificationUtils.progressComplete(builder, getString(R.string.Notification_Restore_TextComplete))
                    notify(NotificationUtils.BACKUP_AND_RESTORE_NOTIFICATION_ID, builder.build())
                    applicationContext.toast(getString(R.string.Message_Success_fromSettings_RestoreComplete))
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()

        _binding.ImageButtonIconBack.setOnClickListener {
            onBackPressed()
        }
    }
}
