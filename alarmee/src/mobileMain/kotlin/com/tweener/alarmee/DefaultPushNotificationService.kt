package com.tweener.alarmee

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
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

    override fun register() {
        scope.launch {
            val token = Firebase.messaging.getToken()
            println("Firebase current token: $token")
        }
    }

    override fun unregister() {
        scope.launch {
            Firebase.messaging.deleteToken()
            println("Firebase push token deleted")
        }
    }

    override fun onMessageReceived(data: Map<String, String>) {
        handleNotificationData(data)
    }
}

internal expect fun handleNotificationData(data: Map<String, String>)
