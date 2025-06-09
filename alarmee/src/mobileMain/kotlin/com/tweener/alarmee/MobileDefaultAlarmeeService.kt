package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import dev.gitlive.firebase.Firebase

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
internal class MobileDefaultAlarmeeService : DefaultAlarmeeService(), MobileAlarmeeService {

    override lateinit var push: PushNotificationService

    override fun initialize(platformConfiguration: AlarmeePlatformConfiguration, firebase: Firebase) {
        init(platformConfiguration = platformConfiguration)

        push = DefaultPushNotificationService(platformConfiguration)
        PushNotificationServiceRegistry.register(push)
    }
}
