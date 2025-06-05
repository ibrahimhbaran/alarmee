package com.tweener.alarmee.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tweener.alarmee.PushNotificationServiceRegistry

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
class AlarmeeFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        println("New Firebase token received: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        println("Firebase message received from: ${message.from}")
        println("Firebase message received data payload: ${message.data}")
        println("Firebase message received notification title: ${message.notification?.title}")
        println("Firebase message received notification body: ${message.notification?.body}")

        if (message.data.isNotEmpty()) {
            PushNotificationServiceRegistry.get()?.onMessageReceived(message.data)
                ?: Log.w("Alarmee", "PushNotificationService not initialized. Ignoring push.")
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()

        println("Firebase messages deleted")
    }
}
