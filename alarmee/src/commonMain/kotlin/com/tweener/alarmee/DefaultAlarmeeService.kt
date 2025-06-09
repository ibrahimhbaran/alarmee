package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
internal open class DefaultAlarmeeService : AlarmeeService {

    private lateinit var config: AlarmeePlatformConfiguration

    override lateinit var local: LocalNotificationService
    private var isInitialized = false

    override fun onAppLaunch(platformConfiguration: AlarmeePlatformConfiguration) {
        // Check if the service is already initialized to prevent re-initialization
        if (isInitialized) {
            println("AlarmeeService is already initialized.")
            return
        }

        initializeFirebase()

        config = platformConfiguration

        // Local notification service
        local = createLocalNotificationService(config)

        isInitialized = true
    }
}

expect fun createAlarmeeService(config: AlarmeePlatformConfiguration): AlarmeeService

expect fun createLocalNotificationService(config: AlarmeePlatformConfiguration): LocalNotificationService

expect fun initializeFirebase()
