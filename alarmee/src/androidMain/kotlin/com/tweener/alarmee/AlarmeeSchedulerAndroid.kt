package com.tweener.alarmee

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.channel.NotificationChannelRegister
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.common._internal.kotlinextensions.getAlarmManager
import com.tweener.common._internal.kotlinextensions.toEpochMilliseconds

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */

@Composable
actual fun rememberAlarmeeScheduler(platformConfiguration: AlarmeePlatformConfiguration): AlarmeeScheduler {
    requirePlatformConfiguration(providedPlatformConfiguration = platformConfiguration, targetPlatformConfiguration = AlarmeeAndroidPlatformConfiguration::class)

    val context = LocalContext.current

    return remember {
        AlarmeeSchedulerAndroid(
            context = context,
            configuration = platformConfiguration,
        )
    }
}

@SuppressLint("ComposableNaming")
class AlarmeeSchedulerAndroid(
    private val context: Context,
    private val configuration: AlarmeeAndroidPlatformConfiguration,
) : AlarmeeScheduler() {

    override fun scheduleAlarm(alarmee: Alarmee, onSuccess: () -> Unit) {
        requireChannelIdNotNull(alarmee = alarmee)

        val pendingIntent = getPendingIntent(context = context, alarmee = alarmee)

        // Create channels
        createNotificationChannels(context = context)

        // Schedule the alarm
        context.getAlarmManager()?.let { alarmManager ->
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmee.scheduledDateTime.toEpochMilliseconds(timeZone = alarmee.timeZone), pendingIntent)

            // Notification scheduled successfully
            onSuccess()
        }
    }

    override fun scheduleRepeatingAlarm(alarmee: Alarmee, repeatInterval: RepeatInterval, onSuccess: () -> Unit) {
        requireChannelIdNotNull(alarmee = alarmee)

        val pendingIntent = getPendingIntent(context = context, alarmee = alarmee)

        // Create channels
        createNotificationChannels(context = context)

        // Schedule the alarm according to the repeat interval
        val intervalMillis = when (repeatInterval) {
            RepeatInterval.HOURLY -> AlarmManager.INTERVAL_HOUR
            RepeatInterval.DAILY -> AlarmManager.INTERVAL_DAY
            RepeatInterval.WEEKLY -> AlarmManager.INTERVAL_DAY * 7
            RepeatInterval.MONTHLY -> AlarmManager.INTERVAL_DAY * 30
            RepeatInterval.YEARLY -> AlarmManager.INTERVAL_DAY * 30 * 12
        }

        context.getAlarmManager()?.let { alarmManager ->
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmee.scheduledDateTime.toEpochMilliseconds(timeZone = alarmee.timeZone), intervalMillis, pendingIntent)

            // Notification scheduled successfully
            onSuccess()
        }
    }

    override fun cancelAlarm(uuid: String) {
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

    private fun createNotificationChannels(context: Context) {
        // Create a notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            require(configuration.notificationChannels.isNotEmpty()) { "At least one ${AlarmeeNotificationChannel::class.simpleName} must be provided." }

            configuration.notificationChannels.forEach { channel ->
                val notificationChannelRegister = NotificationChannelRegister(context = context)
                notificationChannelRegister.register(id = channel.id, name = channel.name, importance = NotificationManager.IMPORTANCE_HIGH)
            }
        }
    }

    private fun getPendingIntent(context: Context, alarmee: Alarmee): PendingIntent {
        // Create the receiver intent with the alarm parameters
        val receiverIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = NotificationBroadcastReceiver.ALARM_ACTION
            putExtra(NotificationBroadcastReceiver.KEY_UUID, alarmee.uuid)
            putExtra(NotificationBroadcastReceiver.KEY_TITLE, alarmee.notificationTitle)
            putExtra(NotificationBroadcastReceiver.KEY_BODY, alarmee.notificationBody)
            putExtra(NotificationBroadcastReceiver.KEY_CHANNEL_ID, alarmee.androidNotificationChannelId)
            putExtra(NotificationBroadcastReceiver.KEY_ICON_RES_ID, configuration.notificationIconResId)
        }

        // Create the broadcast pending intent
        return PendingIntent.getBroadcast(
            context,
            alarmee.uuid.hashCode(),
            receiverIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    /**
     * Makes sure [Alarmee.androidNotificationChannelId] is not null for devices running on Android 0 and above.
     */
    private fun requireChannelIdNotNull(alarmee: Alarmee) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireNotNull(alarmee.androidNotificationChannelId) { "androidNotificationChannelId must not be null to schedule an Alarmee." }
        }
    }
}
