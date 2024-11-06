package com.tweener.alarmee

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.tweener.common._internal.kotlinextension.getAlarmManager
import com.tweener.common._internal.kotlinextensions.toEpochMilliseconds
import io.github.aakira.napier.Napier

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */
class AlarmeeSchedulerAndroid(
    private val context: Context,
    private val notificationIconResId: Int,
    private val notificationChannelId: String,
    private val notificationChannelName: String,
) : AlarmeeScheduler() {

    override fun scheduleAlarm(alarmee: Alarmee) {
        val receiverIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = NotificationBroadcastReceiver.ALARM_ACTION
            putExtra(NotificationBroadcastReceiver.KEY_UUID, alarmee.uuid)
            putExtra(NotificationBroadcastReceiver.KEY_TITLE, alarmee.notificationTitle)
            putExtra(NotificationBroadcastReceiver.KEY_BODY, alarmee.notificationBody)
            putExtra(NotificationBroadcastReceiver.KEY_ICON_RES_ID, notificationIconResId)
            putExtra(NotificationBroadcastReceiver.KEY_CHANNEL_ID, notificationChannelId)
            putExtra(NotificationBroadcastReceiver.KEY_CHANNEL_NAME, notificationChannelName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmee.uuid.hashCode(),
            receiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        // Schedule the alarm with AlarmManager
        context.getAlarmManager()?.let { alarmManager ->
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmee.scheduledDateTime.toEpochMilliseconds(timeZone = alarmee.timeZone), pendingIntent)

            // Notification scheduled successfully
            Napier.d { "Notification with title '${alarmee.notificationTitle}' scheduled at ${alarmee.scheduledDateTime}." }
        }
    }
}
