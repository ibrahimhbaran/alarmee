package com.tweener.alarmee

import com.tweener.common._internal.kotlinextensions.toEpochMilliseconds
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
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
class AlarmeeSchedulerIos : AlarmeeScheduler() {

    override fun scheduleAlarm(alarmee: Alarmee) {
        val content = UNMutableNotificationContent().apply {
            setTitle(alarmee.notificationTitle)
            setBody(alarmee.notificationBody)
        }

        val nsDateTime = NSDate.dateWithTimeIntervalSince1970(secs = alarmee.scheduledDateTime.toEpochMilliseconds(timeZone = alarmee.timeZone) / 1000.0)
        val dateComponents = NSCalendar.currentCalendar.components(
            unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay or NSCalendarUnitHour or NSCalendarUnitMinute,
            fromDate = nsDateTime,
        )

        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(dateComponents = dateComponents, repeats = false)
        val request = UNNotificationRequest.requestWithIdentifier(identifier = alarmee.uuid, content = content, trigger = trigger)

        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.requestAuthorizationWithOptions(options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge) { granted, authorizationError ->
            if (granted) {
                // Schedule the notification
                notificationCenter.addNotificationRequest(request) { requestError ->
                    if (requestError != null) {
                        println("Scheduling notification on iOS failed with error: $requestError")
                    }

                    // Notification scheduled successfully
                    println("Notification with title '${alarmee.notificationTitle}' scheduled at ${alarmee.scheduledDateTime}.")
                }
            } else if (authorizationError != null) {
                println("Error requesting notification permission: $authorizationError")
            }
        }
    }

    override fun cancelAlarm(uuid: String) {
        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.removePendingNotificationRequestsWithIdentifiers(identifiers = listOf(uuid))
    }
}
