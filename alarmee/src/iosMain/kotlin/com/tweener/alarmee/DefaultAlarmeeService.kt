@file:OptIn(ExperimentalForeignApi::class)

package com.tweener.alarmee

import cocoapods.FirebaseMessaging.FIRMessaging
import cocoapods.FirebaseMessaging.FIRMessagingDelegateProtocol
import com.tweener.alarmee.configuration.AlarmeeIosPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.registerForRemoteNotifications
import platform.darwin.NSObject

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

actual fun createLocalNotificationService(config: AlarmeePlatformConfiguration): LocalNotificationService {
    requirePlatformConfiguration(providedPlatformConfiguration = config, targetPlatformConfiguration = AlarmeeIosPlatformConfiguration::class)
    return DefaultLocalNotificationService(config = config)
}

@OptIn(ExperimentalForeignApi::class)
actual fun initializeFirebase() {
    Firebase.initialize()

    FIRMessaging.messaging().delegate = FirebaseMessageDelegate()
    UIApplication.sharedApplication.registerForRemoteNotifications()
}

private class FirebaseMessageDelegate : FIRMessagingDelegateProtocol, NSObject() {

    override fun messaging(messaging: FIRMessaging, didReceiveRegistrationToken: String?) {
        println("Firebase registration token received: $didReceiveRegistrationToken")
    }
}
