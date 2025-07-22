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
     * Unregisters the device from receiving push notifications.
     * Typically used when the user signs out or disables notifications.
     */
    fun unregister()

    /**
     * Handles a push message received from the platform.
     * This function parses the payload and shows a local notification.
     *
     * @param data The key-value payload of the remote push message.
     */
    fun onMessageReceived(data: Map<String, String>)

    /**
     * Retrieves the current Firebase Cloud Messaging (FCM) token for the device.
     * This token uniquely identifies the app instance and is required for sending push notifications.
     *
     * On iOS, this method waits for the APNs token to be set before fetching the FCM token.
     *
     * @return A [Result] containing the FCM token on success, or an exception on failure.
     */
    suspend fun getToken(): Result<String>
}
