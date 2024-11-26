package com.tweener.alarmee

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.tweener.alarmee.android.R
import com.tweener.common._internal.kotlinextensions.getNotificationManager
import com.tweener.common._internal.safeLet

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */
class NotificationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ALARM_ACTION = "com.tweener.alarmee.SET_ALARM"
        const val KEY_UUID = "notificationUuid"
        const val KEY_TITLE = "notificationTitle"
        const val KEY_BODY = "notificationBody"
        const val KEY_PRIORITY = "notificationPriority"
        const val KEY_CHANNEL_ID = "notificationChannelId"
        const val KEY_ICON_RES_ID = "notificationIconResId"

        private val DEFAULT_ICON_RES_ID = R.drawable.ic_notification
        private const val DEFAULT_PRIORITY = NotificationCompat.PRIORITY_DEFAULT
        private const val DEFAULT_CHANNEL_ID = "notificationsChannelId"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ALARM_ACTION) {
            safeLet(
                intent.getStringExtra(KEY_UUID),
                intent.getStringExtra(KEY_TITLE),
                intent.getStringExtra(KEY_BODY),
                intent.getIntExtra(KEY_PRIORITY, DEFAULT_PRIORITY),
                intent.getIntExtra(KEY_ICON_RES_ID, DEFAULT_ICON_RES_ID),
            ) { uuid, title, body, priority, iconResId ->
                // For devices running on Android before Android 0, channelId passed through intents might be null so we used a default channelId that will be ignored
                val channelId = intent.getStringExtra(KEY_CHANNEL_ID) ?: DEFAULT_CHANNEL_ID

                // Create the notification
                val notification = NotificationCompat.Builder(context, channelId)
                    .apply {
                        setSmallIcon(iconResId)
                        setContentTitle(title)
                        setContentText(body)
                        setPriority(priority)
                        setAutoCancel(true)
                        setContentIntent(getPendingIntent(context = context)) // Handles click on notification
                    }
                    .build()

                // Display the notification
                context.getNotificationManager()?.let { notificationManager ->
                    if (notificationManager.areNotificationsEnabled()) {
                        notificationManager.notify(uuid.hashCode(), notification)
                    } else {
                        println("Notifications permission is not granted! Can't show the notification.")
                    }
                }
            }
        }
    }

    private fun getPendingIntent(context: Context): PendingIntent? {
        val intent = context.getLauncherActivityIntent()?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun Context.getLauncherActivityIntent(): Intent? = applicationContext.packageManager.getLaunchIntentForPackage(applicationContext.packageName)
}
