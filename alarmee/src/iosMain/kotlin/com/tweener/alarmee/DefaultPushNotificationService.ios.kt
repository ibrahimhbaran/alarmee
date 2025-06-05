package com.tweener.alarmee

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

internal actual fun handleRemoteMessage(data: Map<String, String>) {
    println("Received remote message on iOS: $data")
}
