[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tweener/alarmee?color=orange)](https://central.sonatype.com/artifact/io.github.tweener/alarmee)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose](https://img.shields.io/badge/compose-1.7.1-blue.svg?logo=jetpackcompose)](https://www.jetbrains.com/lp/compose-multiplatform)
![gradle-version](https://img.shields.io/badge/gradle-8.5.2-blue?logo=gradle)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

[![Website](https://img.shields.io/badge/Author-vivienmahe.com-purple)](https://vivienmahe.com/)
[![X/Twitter](https://img.shields.io/twitter/follow/VivienMahe)](https://twitter.com/VivienMahe)

<br>

![Alarmee logo](https://github.com/user-attachments/assets/c5e72a35-6269-4b29-933e-6ed3e27900f1#gh-light-mode-only)
![Alarmee logo](https://github.com/user-attachments/assets/bcc0da27-1616-4758-a1cb-1d7601734988#gh-dark-mode-only)

---

# Alarmee

**Alarmee** is a Kotlin/Compose Multiplatform llibrary designed to simplify scheduling alarms and notifications on both Android and iOS platforms. With Alarmee, you can schedule one-time or repeating alarms and display platform-specific notifications seamlessly.

<br>

Be sure to show your support by starring ⭐️ this repository, and feel free to [contribute](#-contributing) if you're interested!

---

## 🌟 Features

- 📅 **One-off alarm**: Schedule an alarm to trigger at a specific date and time.
- 🔁 **Repeating alarm**: Schedule recurring alarms with intervals: hourly, daily, weekly, monthly, or yearly.
- **Extensible Configuration**: Customize alarms and notifications with platform-specific settings.

---

## 🛠️ Installation

In your `settings.gradle.kts` file, add Maven Central to your repositories:
```Groovy
repositories {
    mavenCentral()
}
```

Then add Alarmee dependency to your module:

- With version catalog, open `libs.versions.toml`:
```Groovy
[versions]
alarmee = "1.2.0" // Check latest version

[libraries]
alarmee = { group = "io.github.tweener", name = "alarmee", version.ref = "alarmee" }
```

Then in your module `build.gradle.kts` add:
```Groovy
dependencies {
    implementation(libs.alarmee)
}
```

- Without version catalog, in your module `build.gradle.kts` add:
```Groovy
dependencies {
    val alarmee_version = "1.2.0" // Check latest version

    implementation("io.github.tweener:alarmee:$alarmee_version")
}
```

The latest version is: [![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tweener/alarmee?color=orange)](https://central.sonatype.com/artifact/io.github.tweener/alarmee)

---

## 🔧 Configuration

In the `commonModule`, you need to use an instance of a subclass of `AlarmeeScheduler`. Each platform will create the corresponding subclass of the `AlarmeeScheduler`. This can be easily done with dependency injection.

<details>
	<summary>🤖 Android</summary>

In the `androidMain` module, create a `AlarmeeAndroidPlatformConfiguration(...)` instance with the following parameters:
```Kotlin
val platformConfiguration: AlarmeePlatformConfiguration = AlarmeeAndroidPlatformConfiguration(
    notificationIconResId = R.drawable.ic_notification,
    notificationChannels = listOf(
        AlarmeeNotificationChannel(
            id = "dailyNewsChannelId",
            name = "Daily news notifications",
            importance = NotificationManager.IMPORTANCE_HIGH
        ),
        AlarmeeNotificationChannel(
            id = "breakingNewsChannelId",
            name = "Breaking news notifications",
            importance = NotificationManager.IMPORTANCE_LOW
        ),
        // List all the notification channels you need here
    )
)
```
</details>

<details>
	<summary>🍎 iOS</summary>

In your `iosMain` module, create a `AlarmeeIosPlatformConfiguration`:
```Kotlin
val platformConfiguration: AlarmeePlatformConfiguration = AlarmeeIosPlatformConfiguration
```
</details>

---

## 🧑‍💻 Usage

> [!IMPORTANT]
> Before using Alarmee, make sure the Notifications permission is granted on the target platform (Android [official documentation](https://developer.android.com/develop/ui/views/notifications/notification-permission), iOS [official documentation](https://developer.apple.com/documentation/usernotifications/asking-permission-to-use-notifications)).
> 
> Alternativally, you can use [`moko-permissions`](https://github.com/icerockdev/moko-permissions) to easily handle permissions for you.

### 1. Create an instance of AlarmeeScheduler
Depending on your project configuration, you can create an instance of `AlarmeeScheduler` in two different ways:

<details>
	<summary>➡️ Kotlin Multplatform (without Compose)</summary>

- 🤖 Android

  Create an instance of `AlarmeeSchedulerAndroid` with the configuration created previously:
```Kotlin
val alarmeeScheduler: AlarmeeScheduler = AlarmeeSchedulerAndroid(context = context, platformConfiguration = platformConfiguration)
```

- 🍎 iOS

  Create an instance of `AlarmeeSchedulerIos` with the configuration created previously:
```Kotlin
val alarmeeScheduler: AlarmeeScheduler = AlarmeeSchedulerIos(platformConfiguration = platformConfiguration)
```

</details>

<details>
	<summary>➡️ Compose Multplatform</summary>

Using `rememberAlarmeeScheduler(...)` with the configuration created previously:
```Kotlin
val alarmeeScheduler: AlarmeeScheduler = rememberAlarmeeScheduler(platformConfiguration = platformConfiguration)
```
</details>

### 2. Scheduling a one-off alarm
You can schedule an alarm to be triggered at a specific time of the day, using `Alarmee#schedule(...)`. When the alarm is triggered, a notification will be displayed.

For instance, to schedule an alarm on January 12th, 2025, at 5 PM:
```Kotlin
alarmeeScheduler.schedule(
    alarmee = Alarmee(
        uuid = "myAlarmId",
        notificationTitle = "🎉 Congratulations! You've schedule an Alarmee!",
        notificationBody = "This is the notification that will be displayed at the specified date and time.",
        scheduledDateTime = LocalDateTime(year = 2025, month = Month.JANUARY, dayOfMonth = 12, hour = 17, minute = 0),
        androidNotificationConfiguration = AndroidNotificationConfiguration( // Required configuration for Android target only (this parameter is ignored on iOS)
            priority = AndroidNotificationPriority.HIGH,
            channelId = "dailyNewsChannelId",
        )
    )
)
```

### 3. Scheduling a repeating alarm
You can specify a [`RepeatInterval`](https://github.com/Tweener/alarmee/blob/main/alarmee/src/commonMain/kotlin/com/tweener/alarmee/RepeatInterval.kt) parameter, which allows scheduling an alarm to repeat hourly, daily, weekly, monthly, yearly or custom, based on the specified scheduledDateTime.

#### Fixed repeat interval
You can use a fixed repeat interval to schedule an Alarmee every hour, day, week, month, or year.
For instance, to schedule an alarm to start on January 12th, 2025, and repeat every day at 9:30 AM, you can use `RepeatInterval.Daily`:
```Kotlin
alarmeeScheduler.schedule(
    alarmee = Alarmee(
        uuid = "myAlarmId",
        notificationTitle = "🔁 Congratulations! You've schedule a daily repeating Alarmee!",
        notificationBody = "This notification will be displayed on January 12th, 2025, and will repeat every day at 09:30.",
        scheduledDateTime = LocalDateTime(year = 2025, month = Month.JANUARY, dayOfMonth = 12, hour = 9, minute = 30),
        repeatInterval = RepeatInterval.Daily, // Will repeat every day
        androidNotificationConfiguration = AndroidNotificationConfiguration( // Required configuration for Android target only (this parameter is ignored on iOS)
            priority = AndroidNotificationPriority.DEFAULT,
            channelId = "dailyNewsChannelId",
        )
    )
)
```

#### Custom repeat interval
You can also set a custom repeat interval using `RepeatInterval.Custom(duration)` to schedule an Alarmee at a specified duration interval.
For example, to schedule an alarm to start on January 1st, 2025, at 9:30 AM, and repeat every 15 minutes, you can use `RepeatInterval.Custom(duration = 15.minutes)`:
```Kotlin
alarmeeScheduler.schedule(
    alarmee = Alarmee(
        uuid = "myAlarmId",
        notificationTitle = "🔁 Congratulations! You've schedule a custom repeating Alarmee!",
        notificationBody = "This notification will be displayed on January 1st, 2025, at 9:30 AM and will repeat every 15 minutes",
        scheduledDateTime = LocalDateTime(year = 2025, month = Month.JANUARY, dayOfMonth = 1, hour = 9, minute = 30),
        repeatInterval = RepeatInterval.Custom(duration = 15.minutes), // Will repeat every 15 minutes
        androidNotificationConfiguration = AndroidNotificationConfiguration( // Required configuration for Android target only (this parameter is ignored on iOS)
            priority = AndroidNotificationPriority.DEFAULT,
            channelId = "otherChannelId",
        )
    )
)
```

### 4. Cancelling an alarm
An alarm can be cancelled using its uuid, using `Alarmee#cancel(...)`. If an alarm with the specified uuid is found, it will be canceled, preventing any future notifications from being triggered for that alarm.
```Kotlin
alarmeeScheduler.cancel(uuid = "myAlarmId")
```

---

## 👨‍💻 Contributing

We love your input and welcome any contributions! Please read our [contribution guidelines](https://github.com/Tweener/alarmee/blob/master/CONTRIBUTING.md) before submitting a pull request.

---

## 🙏 Credits

- Logo by [Freeicons](https://freeicons.io/essential-collection/alarm-icon-icon-2)

---

## 📜 Licence

Alarmee is licensed under the [Apache-2.0](https://github.com/Tweener/alarmee?tab=Apache-2.0-1-ov-file#readme).
