package com.tweener.alarmee.configuration

/**
 * @author Vivien Mahe
 * @since 14/11/2024
 */
sealed class AlarmeePlatformConfiguration {

    data class Android(
         val notificationIconResId: Int,
         val notificationChannelId: String,
         val notificationChannelName: String,
    ) : AlarmeePlatformConfiguration()

    data object Ios : AlarmeePlatformConfiguration()
}
