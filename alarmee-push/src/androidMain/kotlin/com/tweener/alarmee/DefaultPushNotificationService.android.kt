package com.tweener.alarmee

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.messaging.messaging

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

internal actual fun handleNotificationData(data: Map<String, String>) {
    println("Handling notification data on Android: $data")
}

internal actual suspend fun getFirebaseToken(): String =
    try {
        Firebase.messaging.getToken()
    } catch (throwable: Throwable) {
        throw throwable
    }
