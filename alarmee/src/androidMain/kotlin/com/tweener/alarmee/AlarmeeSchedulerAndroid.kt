package com.tweener.alarmee

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.common._internal.kotlinextensions.getAlarmManager
import com.tweener.common._internal.kotlinextensions.toEpochMilliseconds

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */
@SuppressLint("ComposableNaming")
class AlarmeeSchedulerAndroid(
    private val notificationIconResId: Int,
    private val notificationChannelId: String,
    private val notificationChannelName: String,
) : AlarmeeScheduler() {

    @Composable
    override fun scheduleAlarm(alarmee: Alarmee) {
        val context = LocalContext.current

        // Create the receiver intent with the alarm parameters
        val receiverIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = NotificationBroadcastReceiver.ALARM_ACTION
            putExtra(NotificationBroadcastReceiver.KEY_UUID, alarmee.uuid)
            putExtra(NotificationBroadcastReceiver.KEY_TITLE, alarmee.notificationTitle)
            putExtra(NotificationBroadcastReceiver.KEY_BODY, alarmee.notificationBody)
            putExtra(NotificationBroadcastReceiver.KEY_ICON_RES_ID, notificationIconResId)
            putExtra(NotificationBroadcastReceiver.KEY_CHANNEL_ID, notificationChannelId)
            putExtra(NotificationBroadcastReceiver.KEY_CHANNEL_NAME, notificationChannelName)
        }

        // Create the broadcast pending intent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmee.uuid.hashCode(),
            receiverIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        // Schedule the alarm
        context.getAlarmManager()?.let { alarmManager ->
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmee.scheduledDateTime.toEpochMilliseconds(timeZone = alarmee.timeZone), pendingIntent)

            // Notification scheduled successfully
            println("Notification with title '${alarmee.notificationTitle}' scheduled at ${alarmee.scheduledDateTime}.")
        }
    }

    @Composable
    override fun cancelAlarm(uuid: String) {
        val context = LocalContext.current

        // Create the receiver intent with the alarm parameters
        val receiverIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = NotificationBroadcastReceiver.ALARM_ACTION
            putExtra(NotificationBroadcastReceiver.KEY_UUID, uuid)
        }

        // Create the broadcast pending intent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            uuid.hashCode(),
            receiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        // Cancel the alarm with AlarmManager
        context.getAlarmManager()?.let { alarmManager ->
            alarmManager.cancel(pendingIntent)

            // Notification canceled successfully
            println("Notification with ID '$uuid' canceled.")
        }
    }
}

actual fun createAlarmeeScheduler(platformConfiguration: AlarmeePlatformConfiguration): AlarmeeScheduler {
    requirePlatformConfiguration(providedPlatformConfiguration = platformConfiguration, targetPlatformConfiguration = AlarmeePlatformConfiguration.Android::class)

    return AlarmeeSchedulerAndroid(
        notificationIconResId = platformConfiguration.notificationIconResId,
        notificationChannelId = platformConfiguration.notificationChannelId,
        notificationChannelName = platformConfiguration.notificationChannelName,
    )
}
