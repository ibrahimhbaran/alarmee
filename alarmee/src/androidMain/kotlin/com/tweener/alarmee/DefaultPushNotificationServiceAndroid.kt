package com.tweener.alarmee

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */

internal actual fun handleRemoteMessage(data: Map<String, String>) {
    println("Received remote message on Android: $data")

    // Uncomment the following lines to enable notification handling
    // This requires the appropriate permissions and notification channel setup
//    val title = data["title"] ?: "Reminder"
//    val body = data["body"] ?: "You have something to check"
//
//    val channelId = "alarmee_push"
//
//    val notification = NotificationCompat.Builder(applicationContext, channelId)
//        .setSmallIcon(R.drawable.ic_notification)
//        .setContentTitle(title)
//        .setContentText(body)
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
//        .build()
//
//    if (android.os.Build.VERSION.SDK_INT < 33 ||
//        applicationContext.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED
//    ) {
//        NotificationManagerCompat.from(applicationContext).notify(
//            (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
//            notification
//        )
//    }
}
