package com.tweener.alarmee.configuration

import com.tweener.alarmee.channel.AlarmeeNotificationChannel

/**
 * @author Vivien Mahe
 * @since 20/11/2024
 */
data class AlarmeeAndroidPlatformConfiguration(
    val notificationIconResId: Int,
    val notificationChannels: List<AlarmeeNotificationChannel>,
) : AlarmeePlatformConfiguration
