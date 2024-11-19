package com.tweener.alarmee

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.common._internal.kotlinextensions.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Class to schedule and manage alarms that trigger at specified times of day.
 * Alarms can be configured with various parameters such as time, time zone, and notification content.
 *
 * When the alarm triggers, a notification will be displayed with the specified title and body.
 *
 * @author Vivien Mahe
 * @since 05/11/2024
 */
abstract class AlarmeeScheduler {

    /**
     * Schedules an alarm to be triggered at a specific time of the day. When the alarm is triggered, a notification will be displayed.
     *
     * @param alarmee The [Alarmee] object containing the configuration for the alarm.
     */
    @Composable
    fun schedule(alarmee: Alarmee) {
        // Schedule an alarm with the correct calculated time
        val scheduledDateTime = adjustDateInFuture(alarmee)
        val updatedAlarmee = alarmee.copy(scheduledDateTime = scheduledDateTime)

        updatedAlarmee.repeatInterval
            ?.let { repeatInterval ->
                scheduleRepeatingAlarm(alarmee = alarmee, repeatInterval = repeatInterval) {
                    val message = when (repeatInterval) {
                        RepeatInterval.HOURLY -> "every hour at minute: ${scheduledDateTime.minute}"
                        RepeatInterval.DAILY -> "every day at ${scheduledDateTime.time}"
                        RepeatInterval.WEEKLY -> "every week on ${scheduledDateTime.dayOfWeek} at ${scheduledDateTime.time}"
                        RepeatInterval.MONTHLY -> "every month on day ${scheduledDateTime.dayOfMonth} at ${scheduledDateTime.time}"
                        RepeatInterval.YEARLY -> "every year on the ${scheduledDateTime.month}/${scheduledDateTime.dayOfMonth} at ${scheduledDateTime.time}"
                    }

                    println("Notification with title '${alarmee.notificationTitle}' scheduled $message.")
                }
            }
            ?: run {
                scheduleAlarm(alarmee = alarmee) {
                    println("Notification with title '${alarmee.notificationTitle}' scheduled at ${alarmee.scheduledDateTime}.")
                }
            }
    }

    /**
     * Cancels an existing alarm based on its unique identifier.
     * If an alarm with the specified identifier is found, it will be canceled, preventing any future notifications from being triggered for that alarm.
     *
     * @param uuid The unique identifier for the alarm to be canceled.
     */
    @Composable
    fun cancel(uuid: String) {
        cancelAlarm(uuid = uuid)
    }

    @Composable
    internal abstract fun scheduleAlarm(alarmee: Alarmee, onSuccess: () -> Unit)

    @Composable
    internal abstract fun scheduleRepeatingAlarm(alarmee: Alarmee, repeatInterval: RepeatInterval, onSuccess: () -> Unit)

    @Composable
    internal abstract fun cancelAlarm(uuid: String)

    /**
     * Adjust to the next day if the time has already passed.
     */
    private fun adjustDateInFuture(alarmee: Alarmee): LocalDateTime {
        val now = LocalDateTime.now(timeZone = alarmee.timeZone)

        var scheduledDateTime = alarmee.scheduledDateTime
        if (scheduledDateTime <= now) {
            scheduledDateTime = scheduledDateTime.date.plus(1, DateTimeUnit.DAY).atTime(time = alarmee.scheduledDateTime.time)
        }

        return scheduledDateTime
    }
}

/**
 * Creates an [AlarmeeScheduler] instance and remembers it.
 */
@Composable
fun rememberAlarmeeScheduler(platformConfiguration: AlarmeePlatformConfiguration): AlarmeeScheduler =
    remember {
        createAlarmeeScheduler(platformConfiguration = platformConfiguration)
    }

expect fun createAlarmeeScheduler(platformConfiguration: AlarmeePlatformConfiguration): AlarmeeScheduler

/**
 * Ensures that the provided platform configuration matches the specified target platform type and throws an [IllegalArgumentException] if it does not.
 *
 * @param T The expected type of [AlarmeePlatformConfiguration].
 * @param providedPlatformConfiguration The platform configuration instance to be checked.
 * @param targetPlatformConfiguration The `KClass` of the expected platform configuration type.
 *
 * @throws IllegalArgumentException if [providedPlatformConfiguration] is not an instance of [targetPlatformConfiguration].
 *
 * Example usage:
 * ```
 * val configuration: AlarmeePlatformConfiguration = // obtain configuration
 * requirePlatformConfiguration(configuration, AlarmeePlatformConfiguration.Ios::class)
 * // Now `configuration` is smart-cast to `AlarmeePlatformConfiguration.Ios`
 * ```
 */
@OptIn(ExperimentalContracts::class)
internal inline fun <reified T : AlarmeePlatformConfiguration> requirePlatformConfiguration(
    providedPlatformConfiguration: AlarmeePlatformConfiguration,
    targetPlatformConfiguration: KClass<T>,
) {
    contract {
        returns() implies (providedPlatformConfiguration is T)
    }

    if (providedPlatformConfiguration !is T) {
        throw IllegalArgumentException("Expected ${targetPlatformConfiguration::class.simpleName}")
    }
}
