[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tweener/alarmee?color=orange)](https://central.sonatype.com/artifact/io.github.tweener/alarmee)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
![gradle-version](https://img.shields.io/badge/gradle-8.5.2-blue?logo=gradle)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

[![Website](https://img.shields.io/badge/Author-vivienmahe.com-purple)](https://vivienmahe.com/)
[![X/Twitter](https://img.shields.io/twitter/follow/VivienMahe)](https://twitter.com/VivienMahe)

<br>

![Alarmee logo](https://github.com/user-attachments/assets/c5e72a35-6269-4b29-933e-6ed3e27900f1#gh-light-mode-only)
![Alarmee logo](https://github.com/user-attachments/assets/bcc0da27-1616-4758-a1cb-1d7601734988#gh-dark-mode-only)

---

<h3>A Kotlin Multiplatform library for effortless alarm and local notification scheduling on both Android and iOS.</h3>

Be sure to show your support by starring ⭐️ this repository, and feel free to [contribute](#-contributing) if you're interested!

## ⚙️ Setup

### Installation
In your `build.gradle.kts` file, add Maven Central to your repositories:
```Groovy
repositories {
    mavenCentral()
}
```

Then add Alarmee dependency to your module:

- With version catalog, open `libs.versions.toml`:
```Groovy
[versions]
alarmee = "1.0.0" // Check latest version

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
    val alarmee_version = "1.0.0" // Check latest version

    implementation("io.github.tweener:alarmee:$alarmee_version")
}
```

The latest version is: [![Maven Central Version](https://img.shields.io/maven-central/v/io.github.tweener/alarmee?color=orange)](https://central.sonatype.com/artifact/io.github.tweener/alarmee)

### Platform configurations

In the `commonModule`, you need to use an instance of a subclass of `AlarmeeScheduler`. Each platform will create the corresponding subclass of the `AlarmeeScheduler`. This can be easily done with dependency injection.

<details>
	<summary>🤖 Android</summary>

In the `androidMain` module, create a `AlarmeeSchedulerAndroid(...)` instance with the following configuration:
```Kotlin
val alarmeeScheduler: AlarmeeScheduler = AlarmeeSchedulerAndroid(
    context = context,  
    notificationIconResId = R.drawable.ic_notification,  
    notificationChannelId = "dailyNewsChannelId",  
    notificationChannelName = "Daily news notifications",  
)
```
</details>

<details>
	<summary>🍎 iOS</summary>
    
In your `iosMain` module, create a `AlarmeeSchedulerIos()`, no configuration is required:
```Kotlin
val alarmeeScheduler: AlarmeeScheduler = AlarmeeSchedulerIos()
```
</details>

### Usage
#### Notifications permission
Before using Alarmee, make sure the Notifications permission is granted on the target platform (Android [official documentation](https://developer.android.com/develop/ui/views/notifications/notification-permission), iOS [official documentation](https://developer.apple.com/documentation/usernotifications/asking-permission-to-use-notifications)).

Alternativally, you can use [`moko-permissions`](https://github.com/icerockdev/moko-permissions) to easily handle permissions for you.

#### Scheduling an alarm
You can schedule an alarm to be triggered at a specific time of the day, using `Alarmee#schedule(...)`. When the alarm is triggered, a notification will be displayed.

For instance, to schedule an alarm on January 12th, 2025, at 5 pm:
```Kotlin
alarmeeScheduler.schedule(
    alarmee = Alarmee(
        uuid = "myAlarmId",
        notificationTitle = "🎉 Congratulations! You've schedule an Alarmee!",
        notificationBody = "This is the notification that will be displayed at the specified date and time.",
        scheduledDateTime = LocalDateTime(year = 2025, month = Month.JANUARY, dayOfMonth = 12, hour = 17, minute = 0),
    )
)
```

#### Cancelling an alarm
An alarm can be cancelled using its uuid, using `Alarmee#cancel(...)`. If an alarm with the specified uuid is found, it will be canceled, preventing any future notifications from being triggered for that alarm.
```Kotlin
alarmeeScheduler.cancel(uuid = "myAlarmId")
```

## 👨‍💻 Contributing

We love your input and welcome any contributions! Please read our [contribution guidelines](https://github.com/Tweener/alarmee/blob/master/CONTRIBUTING.md) before submitting a pull request.

## 🙏 Credits

- Logo by [Freeicons](https://freeicons.io/essential-collection/alarm-icon-icon-2)

## 🪪 Licence

Alarmee is licensed under the [Apache-2.0](https://github.com/Tweener/alarmee?tab=Apache-2.0-1-ov-file#readme).
