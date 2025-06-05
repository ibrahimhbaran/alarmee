@file:OptIn(ExperimentalForeignApi::class)

package com.tweener.alarmee

import cocoapods.FirebaseMessaging.FIRMessaging
import cocoapods.FirebaseMessaging.FIRMessagingDelegateProtocol
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
