package com.tweener.alarmee

/**
 * Interface for managing push notifications (remote notifications).
 * Used for registering/unregistering push capabilities and handling incoming push data.
 *
 * This interface is only available on mobile targets (Android & iOS).
 *
 * @author Vivien Mahe
 * @since 05/06/2025
 */

interface PushNotificationService {

    /**
     * Registers the device for receiving push notifications.
     * On Android, this may retrieve the Firebase Cloud Messaging (FCM) token.
     * On iOS, this requests permission and registers for remote notifications.
     */
    fun register()

    /**
     * Unregisters the device from receiving push notifications.
     * Typically used when the user signs out or disables notifications.
     */
    fun unregister()

    /**
     * Handles a push message received from the platform (e.g. FCM on Android).
     * This function should parse the payload and show a local notification,
     * or trigger custom logic depending on the app’s needs.
     *
     * @param data The key-value payload of the remote push message.
     */
    fun onMessageReceived(data: Map<String, String>)

}
