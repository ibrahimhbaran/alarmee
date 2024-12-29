package com.tweener.alarmee

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeeJvmPlatformConfiguration
import java.awt.*
import java.awt.TrayIcon.MessageType

/**
 * @author Vivien Mahe
 * @since 28/11/2024
 */

@Composable
actual fun rememberAlarmeeScheduler(platformConfiguration: AlarmeePlatformConfiguration): AlarmeeScheduler {
    requirePlatformConfiguration(providedPlatformConfiguration = platformConfiguration, targetPlatformConfiguration = AlarmeeJvmPlatformConfiguration::class)

    return remember { AlarmeeSchedulerJvm(configuration = platformConfiguration) }
}

class AlarmeeSchedulerJvm(
    private val configuration: AlarmeeJvmPlatformConfiguration = AlarmeeJvmPlatformConfiguration,
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
        if (SystemTray.isSupported()) {
            val tray = SystemTray.getSystemTray()
            val image = Toolkit.getDefaultToolkit().createImage("icon.png")
            val trayIcon = TrayIcon(image, "Tray Demo")
            trayIcon.isImageAutoSize = true
            trayIcon.toolTip = "System tray icon demo"
            trayIcon.displayMessage(title, body, MessageType.INFO)
            tray.add(trayIcon)
        } else {
            println("System tray not supported!")
        }
    }
}
