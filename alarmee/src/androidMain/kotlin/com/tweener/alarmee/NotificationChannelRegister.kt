package com.tweener.alarmee

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.tweener.common._internal.kotlinextensions.getNotificationManager
import io.github.aakira.napier.Napier

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */
class NotificationChannelRegister(
    private val context: Context,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun register(id: String, name: String, importance: Int = NotificationManager.IMPORTANCE_DEFAULT, description: String? = null) {
        context.getNotificationManager()?.let { notificationManager ->
            val channel = NotificationChannel(id, name, importance).apply { description?.let { this.description = description } }
            notificationManager.createNotificationChannel(channel)

            Napier.d { "Notification channel '${channel.name}' (ID: '${channel.id}') created!" }
        }
    }
}
