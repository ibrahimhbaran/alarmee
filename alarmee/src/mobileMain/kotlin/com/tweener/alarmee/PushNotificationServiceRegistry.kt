package com.tweener.alarmee

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

object PushNotificationServiceRegistry {

    private var service: PushNotificationService? = null

    fun register(service: PushNotificationService) {
        this.service = service
    }

    fun get(): PushNotificationService? = service

    fun clear() {
        service = null
    }
}

