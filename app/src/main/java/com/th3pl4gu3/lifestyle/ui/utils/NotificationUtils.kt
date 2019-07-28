package com.th3pl4gu3.lifestyle.ui.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.th3pl4gu3.lifestyle.R
import com.th3pl4gu3.lifestyle.core.utils.*

class NotificationUtils {

    companion object {

            /*
             * This notification ID can be used to access our notification after we've displayed it. This
             * can be handy when we need to cancel the notification, or perhaps update it. This number is
             * arbitrary and can be set to whatever you like.
             */

        val BACKUP_AND_RESTORE_NOTIFICATION_ID = 16

        fun showBackupProgressNotification(
            context: Context,
            notificationName: String,
            notificationContentText: String
        ): NotificationCompat.Builder {
            //Build Channel
            val name = VALUE_NOTIFICATION_CHANNEL_NAME_BACKUP_RESTORE_
            val descriptionText = VALUE_NOTIFICATION_CHANNEL_DESCRIPTION_BACKUP_RESTORE_
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(VALUE_NOTIFICATION_CHANNEL_ID_BACKUP_RESTORE_, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            //Build Notification
            return NotificationCompat.Builder(context, VALUE_NOTIFICATION_CHANNEL_ID_BACKUP_RESTORE_)
                .setSmallIcon(R.drawable.ic_backup_dark)
                .setContentTitle(notificationName)
                .setContentText(notificationContentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
        }

        fun showRestoreProgressNotification(
            context: Context,
            notificationName: String,
            notificationContentText: String
        ): NotificationCompat.Builder {
            //Build Channel
            val name = VALUE_NOTIFICATION_CHANNEL_NAME_BACKUP_RESTORE_
            val descriptionText = VALUE_NOTIFICATION_CHANNEL_DESCRIPTION_BACKUP_RESTORE_
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(VALUE_NOTIFICATION_CHANNEL_ID_BACKUP_RESTORE_, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            //Build Notification
            return NotificationCompat.Builder(context, VALUE_NOTIFICATION_CHANNEL_ID_BACKUP_RESTORE_)
                .setSmallIcon(R.drawable.ic_restore_dark)
                .setContentTitle(notificationName)
                .setContentText(notificationContentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
        }

        fun progressComplete(builder: NotificationCompat.Builder, contentText: String) {
            builder.setOngoing(false)
            builder.setContentText(contentText)
            builder.setProgress(0, 0, false)
        }
    }
}