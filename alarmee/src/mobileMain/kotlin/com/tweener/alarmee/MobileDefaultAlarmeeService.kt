package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
internal class MobileDefaultAlarmeeService : DefaultAlarmeeService(), MobileAlarmeeService {

    override lateinit var push: PushNotificationService

    override fun initialize(platformConfiguration: AlarmeePlatformConfiguration) {
        initializeFirebase()
        init(platformConfiguration = platformConfiguration)

        push = DefaultPushNotificationService(platformConfiguration)
        PushNotificationServiceRegistry.register(push)
    }
}
