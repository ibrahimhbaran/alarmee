package com.tweener.alarmee

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tweener.alarmee.configuration.AlarmeeIosPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.common._internal.kotlinextensions.toEpochMilliseconds
import kotlinx.datetime.isoDayNumber
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

/**
 * @author Vivien Mahe
 * @since 06/11/2024
 */

@Composable
actual fun rememberAlarmeeScheduler(platformConfiguration: AlarmeePlatformConfiguration): AlarmeeScheduler {
    requirePlatformConfiguration(providedPlatformConfiguration = platformConfiguration, targetPlatformConfiguration = AlarmeeIosPlatformConfiguration::class)

    return remember { AlarmeeSchedulerIos(configuration = platformConfiguration) }
}

class AlarmeeSchedulerIos(
    private val configuration: AlarmeeIosPlatformConfiguration = AlarmeeIosPlatformConfiguration,
) : AlarmeeScheduler() {

    override fun scheduleAlarm(alarmee: Alarmee, onSuccess: () -> Unit) {
        val nsDateTime = NSDate.dateWithTimeIntervalSince1970(secs = alarmee.scheduledDateTime.toEpochMilliseconds(timeZone = alarmee.timeZone) / 1000.0)
        val dateComponents = NSCalendar.currentCalendar.components(
            unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay or NSCalendarUnitHour or NSCalendarUnitMinute,
            fromDate = nsDateTime,
        )

        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(dateComponents = dateComponents, repeats = false)

        configureNotification(alarmee = alarmee, notificationTrigger = trigger, onSuccess = onSuccess)
    }

    override fun scheduleRepeatingAlarm(alarmee: Alarmee, repeatInterval: RepeatInterval, onSuccess: () -> Unit) {
        val dateComponents = NSDateComponents()
        dateComponents.calendar = NSCalendar.currentCalendar

        dateComponents.minute = alarmee.scheduledDateTime.minute.toLong()

        when (repeatInterval) {
            RepeatInterval.HOURLY -> Unit // No need to set specific date or hour; repeats every hour

            RepeatInterval.DAILY -> {
                dateComponents.hour = alarmee.scheduledDateTime.hour.toLong()
            }

            RepeatInterval.WEEKLY -> {
                dateComponents.hour = alarmee.scheduledDateTime.hour.toLong()
                dateComponents.weekday = alarmee.scheduledDateTime.dayOfWeek.isoDayNumber.toLong()
            }

            RepeatInterval.MONTHLY -> {
                dateComponents.hour = alarmee.scheduledDateTime.hour.toLong()
                dateComponents.day = alarmee.scheduledDateTime.dayOfMonth.toLong()
            }

            RepeatInterval.YEARLY -> {
                dateComponents.hour = alarmee.scheduledDateTime.hour.toLong()
                dateComponents.day = alarmee.scheduledDateTime.dayOfMonth.toLong()
                dateComponents.month = alarmee.scheduledDateTime.monthNumber.toLong()
            }
        }

        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(dateComponents = dateComponents, repeats = true)

        configureNotification(alarmee = alarmee, notificationTrigger = trigger, onSuccess = onSuccess)
    }

    override fun cancelAlarm(uuid: String) {
        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.removePendingNotificationRequestsWithIdentifiers(identifiers = listOf(uuid))
    }

    private fun configureNotification(alarmee: Alarmee, notificationTrigger: UNCalendarNotificationTrigger, onSuccess: () -> Unit) {
        val content = UNMutableNotificationContent().apply {
            setTitle(alarmee.notificationTitle)
            setBody(alarmee.notificationBody)
        }

        val request = UNNotificationRequest.requestWithIdentifier(identifier = alarmee.uuid, content = content, trigger = notificationTrigger)

        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.requestAuthorizationWithOptions(options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge) { granted, authorizationError ->
            if (granted) {
                // Schedule the notification
                notificationCenter.addNotificationRequest(request) { requestError ->
                    if (requestError != null) {
                        println("Scheduling notification on iOS failed with error: $requestError")
                    }

                    // Notification scheduled successfully
                    onSuccess()
                }
            } else if (authorizationError != null) {
                println("Error requesting notification permission: $authorizationError")
            }
        }
    }
}
