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
import com.tweener.alarmee.RepeatInterval
import com.tweener.alarmee.rememberAlarmeeScheduler
import com.tweener.alarmee.sample.ui.theme.AlarmeeTheme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

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
                            notificationBody = "This is the notification that will be displayed at the specified date and time.",
                            scheduledDateTime = LocalDateTime(year = 2024, month = Month.NOVEMBER, dayOfMonth = 26, hour = 11, minute = 36),
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                priority = AndroidNotificationPriority.DEFAULT,
                                notificationChannelId = "dailyNewsChannelId",
                            ),
                        )
                    )
                }) { Text("Set a one-off Alarmee") }

                Button(onClick = {
                    alarmeeScheduler.schedule(
                        alarmee = Alarmee(
                            uuid = "myRepeatingAlarmId",
                            notificationTitle = "🔁 Congratulations! You've schedule a repeating Alarmee!",
                            notificationBody = "This is the notification that will be displayed every day at 09:36.",
                            scheduledDateTime = LocalDateTime(year = 2024, month = Month.NOVEMBER, dayOfMonth = 26, hour = 11, minute = 36),
                            repeatInterval = RepeatInterval.DAILY,
                            androidNotificationConfiguration = AndroidNotificationConfiguration(
                                priority = AndroidNotificationPriority.MAXIMUM,
                                notificationChannelId = "breakingNewsChannelId",
                            ),
                        )
                    )
                }) { Text("Set a repeating Alarmee") }
            }
        }
    }
}
