package com.tweener.alarmee

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

internal actual fun handleNotificationData(data: Map<String, String>) {
    // On iOS, we let the system display the notification. But we can still get the data attached to the notification.
    println("Handling notification data on iOS: $data")
}
