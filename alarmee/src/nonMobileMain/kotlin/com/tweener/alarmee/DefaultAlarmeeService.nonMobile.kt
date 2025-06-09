package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfigurationNonMobile

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

actual fun initializeFirebase() {
    // No-op for non-mobile platforms
}

actual fun createAlarmeeService(config: AlarmeePlatformConfiguration): AlarmeeService =
    DefaultAlarmeeService().apply {
        onAppLaunch(platformConfiguration = config)
    }

actual fun createLocalNotificationService(config: AlarmeePlatformConfiguration): LocalNotificationService {
    requirePlatformConfiguration(providedPlatformConfiguration = config, targetPlatformConfiguration = AlarmeePlatformConfigurationNonMobile::class)
    return DefaultLocalNotificationService(config = config)
}
