package com.tweener.alarmee.service

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

        val title = message.data["title"]
        val body = message.data["body"]

        println("Firebase message received data payload: ${message.data}")
        println("Firebase message received notification title: $title")
        println("Firebase message received notification body: $body")

        if (message.data.isNotEmpty()) {
            PushNotificationServiceRegistry.get()?.onMessageReceived(message.data)
                ?: println("PushNotificationService not initialized. Ignoring push.")
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()

        println("Firebase messages deleted")
    }
}
