package com.tweener.alarmee.channel

import android.app.NotificationManager

/**
 * @author Vivien Mahe
 * @since 20/11/2024
 */
data class AlarmeeNotificationChannel(
    val id: String,
    val name: String,
    val importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
)
