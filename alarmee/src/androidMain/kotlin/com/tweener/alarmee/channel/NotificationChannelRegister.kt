package com.tweener.alarmee.channel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.tweener.common._internal.kotlinextensions.getNotificationManager

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */
class NotificationChannelRegister(
    private val context: Context,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun register(
        id: String,
        name: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
        description: String? = null
    ) {
        context.getNotificationManager()?.let { notificationManager ->
            val channelDoesNotExists = notificationManager.notificationChannels.none { it.id == id }

            // Only create channel if it does not exists yet
            if (channelDoesNotExists) {
                notificationManager.getNotificationChannel(id)
                val channel = NotificationChannel(id, name, importance).apply { description?.let { this.description = description } }
                notificationManager.createNotificationChannel(channel)

                println("Notification channel '${channel.name}' (ID: '${channel.id}') created!")
            }
        }
    }
}
