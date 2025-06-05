package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

actual fun createAlarmeeService(config: AlarmeePlatformConfiguration): AlarmeeService =
    MobileDefaultAlarmeeService().apply {
        onAppLaunch(platformConfiguration = config)
    }
