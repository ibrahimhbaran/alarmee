package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import dev.gitlive.firebase.Firebase

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
internal class MobileDefaultAlarmeeService : DefaultAlarmeeService(), MobileAlarmeeService {

    override lateinit var push: PushNotificationService
    private var isInitialized = false

    override fun initialize(platformConfiguration: AlarmeePlatformConfiguration, firebase: Firebase?) {
        super.initialize(platformConfiguration = platformConfiguration)

        // Check if the service is already initialized to prevent re-initialization
        if (isInitialized) {
            println("Alarmee is already initialized.")
            return
        }

        // No need to initialize Firebase if it's already done
        if (firebase == null) {
            initializeFirebase()
        }

        configureFirebase()

        // Initialize push notification service
        push = DefaultPushNotificationService(platformConfiguration)
        PushNotificationServiceRegistry.register(push)

        isInitialized = true
    }
}

internal expect fun initializeFirebase()

internal expect fun configureFirebase()
