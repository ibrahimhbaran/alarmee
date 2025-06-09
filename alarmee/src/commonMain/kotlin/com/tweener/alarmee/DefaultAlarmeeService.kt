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

    override fun initialize(platformConfiguration: AlarmeePlatformConfiguration) {
        initializeFirebase()
        init(platformConfiguration = platformConfiguration)
    }

    protected fun init(platformConfiguration: AlarmeePlatformConfiguration) {
        // Check if the service is already initialized to prevent re-initialization
        if (isInitialized) {
            println("AlarmeeService is already initialized.")
            return
        }

        configureFirebase()

        config = platformConfiguration

        // Local notification service
        local = createLocalNotificationService(config)

        isInitialized = true

        println("Alarmee is initialized.")
    }
}

expect fun createAlarmeeService(): AlarmeeService

internal expect fun createLocalNotificationService(config: AlarmeePlatformConfiguration): LocalNotificationService

internal expect fun initializeFirebase()

internal expect fun configureFirebase()
