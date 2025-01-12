package com.tweener.alarmee.sample

/**
 * @author Vivien Mahe
 * @since 25/11/2024
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tweener.alarmee.Alarmee
import com.tweener.alarmee.AlarmeeScheduler
import com.tweener.alarmee.AndroidNotificationConfiguration
import com.tweener.alarmee.AndroidNotificationPriority
import com.tweener.alarmee.IosNotificationConfiguration
import com.tweener.alarmee.RepeatInterval
import com.tweener.alarmee.rememberAlarmeeScheduler
import com.tweener.alarmee.sample.ui.theme.AlarmeeTheme
import com.tweener.kmpkit.kotlinextensions.now
import com.tweener.kmpkit.kotlinextensions.plus
import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun App() {
    val alarmeeScheduler: AlarmeeScheduler = rememberAlarmeeScheduler(platformConfiguration = createAlarmeePlatformConfiguration())

    AlarmeeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.CenterVertically),
            ) {
                Button(onClick = {
                    alarmeeScheduler.schedule(
                        alarmee = Alarmee(
                            uuid = "myOneOffAlarmId",
                            notificationTitle = "🎉 Congratulations! You've schedule a one-off Alarmee!",
                            notificationBody = "This is the notification that will be displayed 10 seconds from now.",
                            scheduledDateTime = LocalDateTime.now().plus(10.seconds),
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                priority = AndroidNotificationPriority.DEFAULT,
                                channelId = "dailyNewsChannelId",
                            ),
                            iosNotificationConfiguration = IosNotificationConfiguration(),
                        )
                    )
                }) { Text("Set a one-off Alarmee") }

                Button(onClick = {
                    val now = LocalDateTime.now()
                    val scheduledDateTime = LocalDateTime(year = now.year, month = now.month, dayOfMonth = now.dayOfMonth, hour = 9, minute = 36, second = 0)

                    alarmeeScheduler.schedule(
                        alarmee = Alarmee(
                            uuid = "myRepeatingAlarmId",
                            notificationTitle = "🔁 Congratulations! You've schedule a daily repeating Alarmee!",
                            notificationBody = "This notification will be displayed every day at 09:36.",
                            scheduledDateTime = scheduledDateTime,
                            repeatInterval = RepeatInterval.Daily,
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                priority = AndroidNotificationPriority.HIGH,
                                channelId = "breakingNewsChannelId",
                            ),
                            iosNotificationConfiguration = IosNotificationConfiguration(),
                        )
                    )
                }) { Text("Set a daily repeating Alarmee") }

                Button(onClick = {
                    alarmeeScheduler.schedule(
                        alarmee = Alarmee(
                            uuid = "myRepeatingAlarmId",
                            notificationTitle = "🔁 Congratulations! You've schedule a custom repeating Alarmee!",
                            notificationBody = "This is the notification that will be displayed every 15 minutes.",
                            repeatInterval = RepeatInterval.Custom(duration = 15.minutes),
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                priority = AndroidNotificationPriority.MAXIMUM,
                                channelId = "breakingNewsChannelId",
                            ),
                            iosNotificationConfiguration = IosNotificationConfiguration(),
                        )
                    )
                }) { Text("Set a custom repeating Alarmee") }

                Button(onClick = {
                    alarmeeScheduler.push(
                        alarmee = Alarmee(
                            uuid = "immediateNotificationId",
                            notificationTitle = "🚀 Immediate Notification",
                            notificationBody = "This is an immediate notification pushed without any schedule.",
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                priority = AndroidNotificationPriority.MINIMUM,
                                channelId = "immediateChannelId",
                            ),
                            iosNotificationConfiguration = IosNotificationConfiguration(),
                        )
                    )
                }) { Text("Push Notification Now") }

                Button(onClick = {
                    alarmeeScheduler.push(
                        alarmee = Alarmee(
                            uuid = "soundNotificationId",
                            notificationTitle = "🔈 Notification with custom sound",
                            notificationBody = "This is a notification with a custom sound.",
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                priority = AndroidNotificationPriority.HIGH,
                                channelId = "soundChannelId",
                            ),
                            iosNotificationConfiguration = IosNotificationConfiguration(
                                soundFilename = "notifications.wav",
                            ),
                        )
                    )
                }) { Text("Push a notification with sound") }
            }
        }
    }
}
