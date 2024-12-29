package com.tweener.alarmee

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tweener.alarmee.configuration.AlarmeeJsPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

/**
 * @author Vivien Mahe
 * @since 28/11/2024
 */

@Composable
actual fun rememberAlarmeeScheduler(platformConfiguration: AlarmeePlatformConfiguration): AlarmeeScheduler {
    requirePlatformConfiguration(providedPlatformConfiguration = platformConfiguration, targetPlatformConfiguration = AlarmeeJsPlatformConfiguration::class)

    return remember { AlarmeeSchedulerJs(configuration = platformConfiguration) }
}

class AlarmeeSchedulerJs(
    private val configuration: AlarmeeJsPlatformConfiguration = AlarmeeJsPlatformConfiguration,
) : AlarmeeScheduler() {

    override fun scheduleAlarm(alarmee: Alarmee, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun scheduleRepeatingAlarm(alarmee: Alarmee, repeatInterval: RepeatInterval, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun cancelAlarm(uuid: String) {
        TODO("Not yet implemented")
    }

    override fun pushNotificationNow(uuid: String, title: String, body: String, channelId: String, priority: AndroidNotificationPriority) {
        if (!("Notification" in js("window"))) {
            println("This browser does not support notifications.")
            return
        }

        val permission = js("Notification.permission").toString()
        if (permission == "granted") {
            val notification = js("new Notification(title, { body: body })")
        } else if (permission != "denied") {
            js("Notification.requestPermission()").then { result ->
                if (result == "granted") {
                    val notification = js("new Notification(title, { body: body })")
                }
            }
        }
    }
}
