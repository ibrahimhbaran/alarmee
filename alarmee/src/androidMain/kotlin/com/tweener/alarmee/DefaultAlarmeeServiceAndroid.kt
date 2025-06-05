package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

actual fun createLocalNotificationService(config: AlarmeePlatformConfiguration): LocalNotificationService {
    requirePlatformConfiguration(providedPlatformConfiguration = config, targetPlatformConfiguration = AlarmeeAndroidPlatformConfiguration::class)
    return DefaultLocalNotificationService(config = config)
}

actual fun initializeFirebase() {
    // Nothing to do here
}
