package com.tweener.alarmee

import com.tweener.common._internal.kotlinextensions.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime
import kotlinx.datetime.plus

/**
 * Class to schedule an alarm to trigger at a specified time of day. Alarms can be configured with various parameters such as time, time zone, and notification content.
 *
 * When the alarm triggers, a notification will be displayed with the specified title and body.
 *
 * @author Vivien Mahe
 * @since 05/11/2024
 */
class AlarmeeScheduler {

    /**
     * Schedules an alarm to be triggered at a specific time of the day. When the alarm is triggered, a notification will be displayed.
     *
     * @param alarmee The [Alarmee] object containing the configuration for the alarm.
     */
    fun schedule(alarmee: Alarmee) {
        val timeZone = alarmee.timeZone
        val now = LocalDateTime.now(timeZone = timeZone)

        // Adjust to the next day if the time has already passed
        var scheduledDateTime = alarmee.scheduledDateTime
        if (scheduledDateTime <= now) {
            scheduledDateTime = scheduledDateTime.date.plus(1, DateTimeUnit.DAY).atTime(time = alarmee.scheduledDateTime.time)
        }

        // Schedule an alarm with the correct calculated time
        val updatedAlarmee = alarmee.copy(scheduledDateTime = scheduledDateTime)
        scheduleAlarm(alarmee = updatedAlarmee)
    }
}

internal expect fun scheduleAlarm(alarmee: Alarmee)
