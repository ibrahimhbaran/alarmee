package com.tweener.alarmee.service

import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tweener.alarmee.DEFAULT_NOTIFICATION_CHANNEL_ID
import com.tweener.alarmee.notification.NotificationFactory
import com.tweener.alarmee.notification.NotificationFactory.Companion.DEEP_LINK_URI_PARAM
import com.tweener.alarmee.reveicer.NotificationBroadcastReceiver.Companion.DEFAULT_ICON_COLOR
import com.tweener.alarmee.reveicer.NotificationBroadcastReceiver.Companion.DEFAULT_ICON_RES_ID
import com.tweener.kmpkit.kotlinextensions.getNotificationManager
import com.tweener.kmpkit.utils.safeLet
import java.util.UUID

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
class AlarmeeFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

//        println("New Firebase token received: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        safeLet(message.data["title"], message.data["body"]) { title, body ->
            val deepLinkUri = message.data[DEEP_LINK_URI_PARAM]

            val notification = NotificationFactory.create(
                context = com.tweener.alarmee._internal.applicationContext,
                channelId = DEFAULT_NOTIFICATION_CHANNEL_ID,
                title = title,
                body = body,
                priority = NotificationCompat.PRIORITY_DEFAULT,
                iconResId = DEFAULT_ICON_RES_ID,
                iconColor = DEFAULT_ICON_COLOR.toArgb(),
                deepLinkUri = deepLinkUri,
            )

            applicationContext.getNotificationManager()?.let { notificationManager ->
                if (notificationManager.areNotificationsEnabled()) {
                    notificationManager.notify(UUID.randomUUID().toString().hashCode(), notification)
                } else {
                    println("Notifications permission is not granted! Can't show the notification.")
                }
            }
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()

        println("Firebase messages deleted")
    }
}
