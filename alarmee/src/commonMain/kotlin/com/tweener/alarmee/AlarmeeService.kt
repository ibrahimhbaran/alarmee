package com.tweener.alarmee

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

@Composable
fun rememberAlarmeeService(
    platformConfiguration: AlarmeePlatformConfiguration
): AlarmeeService =
    remember(platformConfiguration) {
        createAlarmeeService(platformConfiguration).apply {
            onAppLaunch(platformConfiguration)
        }
    }

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
interface AlarmeeService {

    val local: LocalNotificationService

    /**
     * This method needs to be called when the app is launched. For instance, in your `App` root composable function.
     */
    fun onAppLaunch(platformConfiguration: AlarmeePlatformConfiguration)

}
