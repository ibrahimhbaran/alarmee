@file:OptIn(ExperimentalForeignApi::class)

package com.tweener.alarmee

import cocoapods.FirebaseMessaging.FIRMessaging
import cocoapods.FirebaseMessaging.FIRMessagingDelegateProtocol
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat
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

        val hexToken = messaging.APNSToken?.toHexString()
        println("APNS current token: $hexToken")
    }
}

private fun NSData.toHexString(): String {
    val buffer = bytes?.reinterpret<ByteVar>() ?: return ""
    val size = length.toInt()
    val result = StringBuilder()

    for (i in 0 until size) {
        val byte = buffer[i]
        val hex = NSString.stringWithFormat("%02x", byte.toUByte().toInt())
        result.append(hex)
    }

    return result.toString()
}
