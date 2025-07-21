package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.kmpkit.thread.suspendCatching
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.messaging.messaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
internal class DefaultPushNotificationService(
    private val config: AlarmeePlatformConfiguration
) : PushNotificationService {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun unregister() {
        scope.launch {
            Firebase.messaging.deleteToken()
            println("Firebase push token deleted")
        }
    }

    override fun onMessageReceived(data: Map<String, String>) {
        handleNotificationData(data)
    }

    override suspend fun getToken(): Result<String> = suspendCatching {
        getFirebaseToken()
    }.onFailure { throwable ->
        println("Error getting Firebase token: $throwable")
    }
}

internal expect fun handleNotificationData(data: Map<String, String>)

internal expect suspend fun getFirebaseToken(): String
