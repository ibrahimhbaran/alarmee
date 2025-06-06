package com.tweener.alarmee.reveicer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.tweener.alarmee.android.R
import com.tweener.alarmee.notification.NotificationFactory
import com.tweener.kmpkit.kotlinextensions.getNotificationManager
import com.tweener.kmpkit.utils.safeLet

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
        const val KEY_ICON_COLOR = "notificationColor"
        const val KEY_SOUND_FILENAME = "notificationSoundFilename"
        const val KEY_DEEP_LINK_URI = "notificationDeepLinkUri"

        val DEFAULT_ICON_RES_ID = R.drawable.ic_notification
        val DEFAULT_ICON_COLOR = Color.Transparent
        private const val DEFAULT_PRIORITY = NotificationCompat.PRIORITY_DEFAULT
        private const val DEFAULT_CHANNEL_ID = "notificationsChannelId"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ALARM_ACTION) {
            safeLet(
                intent.getStringExtra(KEY_UUID),
                intent.getStringExtra(KEY_TITLE),
                intent.getStringExtra(KEY_BODY),
            ) { uuid, title, body ->
                val priority = intent.getIntExtra(KEY_PRIORITY, DEFAULT_PRIORITY)
                val iconResId = intent.getIntExtra(KEY_ICON_RES_ID, DEFAULT_ICON_RES_ID)
                val iconColor = intent.getIntExtra(KEY_ICON_COLOR, DEFAULT_ICON_COLOR.toArgb())
                val soundFilename = intent.getStringExtra(KEY_SOUND_FILENAME)
                val deepLinkUri = intent.getStringExtra(KEY_DEEP_LINK_URI)

                // For devices running on Android before Android 0, channelId passed through intents might be null so we used a default channelId that will be ignored
                val channelId = intent.getStringExtra(KEY_CHANNEL_ID) ?: DEFAULT_CHANNEL_ID

                // Create the notification
                val notification = NotificationFactory.create(
                    context = context,
                    channelId = channelId,
                    title = title,
                    body = body,
                    priority = priority,
                    iconResId = iconResId,
                    iconColor = iconColor,
                    soundFilename = soundFilename,
                    deepLinkUri = deepLinkUri,
                )

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
}
