# Changelog

## [2.1.3] - July 15, 2025
- 🔄 Update KMPKit to 1.0.10, which uses latest version of `kotlinx-datetime:0.7.1`.

## [2.1.2] - July 13, 2025
- It is now possible to initialize Alarmee via Compose, on mobile targets, with an already existing Firebase instance.

## [2.1.1] - July 7, 2025
- Fix Alarmee missing initialize method when a Firebase instance already exists.

## [2.1.0] - July 6, 2025
- 🎉 New feature: Notifications can now display images, from both local and push sources.
- 🔄 Update Kotlin to 2.2.0.
- 🔄 Update Compose Multiplatform to 1.8.2.

## [2.0.2] - June 12, 2025
- 🤖 On Android, create a default channel notification if none was provided in the `AlarmeeAndroidPlatformConfiguration`, to make sure there is always at least one.

## [2.0.1] - June 9, 2025
- Allow Alarmee to be initialized by passing a Firebase instance, for mobile targets (Android & iOS).
- Exposes the FCM token via `PushNotificationService#getToken()`, for mobile targets (Android & iOS).

## [2.0.0] - June 9, 2025
🚀 Alarmee 2.0 is here!
- 🎉 New feature: Push notifications on iOS and Android!
- The API structure has been revamped with a focus on clearer service boundaries and support for both local and push notifications.

Check out the [migration guide from version 1.x to 2.0](https://github.com/Tweener/alarmee?tab=readme-ov-file#-migration-guide-from-alarmee-1x-to-20).

## [1.6.4] - May 25, 2025
- 🎉 New feature to add `deepLinkUri` support to `Alarmee` (Requested by [@M1r7](https://github.com/M1r7) in [https://github.com/Tweener/alarmee/pull/7](https://github.com/Tweener/alarmee/issues/18))

## [1.6.3] - May 13, 2025
- 🔄 Update Kotlin to 2.1.21.
- 🔄 Update Compose Multiplatform to 1.8.0.
- 🔄 Update KMPKit to 1.0.7.

## [1.6.2] - April 15, 2025
- 🔄 Update Kotlin to 2.1.20.

## [1.6.1] - January 24, 2025
- 🔄 Update Kotlin to 2.1.0.
- 🔄 Update Compose Multiplatform to 1.7.3.
- 🔄 Update Gradle to 8.11.1.
- 🍎 On iOS, badge is now optional to avoid automatically setting it to 0 when not provided.

## [1.6.0] - January 12, 2025
- 🎉 New feature to set custom notification sounds 🔈 on both Android and iOS. Details [here](https://github.com/Tweener/alarmee/blob/main/README.md#notification-sound).
- 🍎 On iOS, new feature to set the badge number on notifications. Details [here](https://github.com/Tweener/alarmee/blob/main/README.md#notification-badge).
- **`[BREAKING]`** 🤖 On Android, `AndroidNotificationConfiguration#notificationIconResId` and `AndroidNotificationConfiguration#notificationIconColor` have been renamed respectively `AndroidNotificationConfiguration#iconResId` and `AndroidNotificationConfiguration#iconColor` for naming consistency.

## [1.5.0] - January 4, 2025
- 🎉 New feature to directly push Alarmees right away without scheduling by [@Zubayer204](https://github.com/Zubayer204) in https://github.com/Tweener/alarmee/pull/7
- Improves validation of an Alarmee and adjusting date in future.
- 🍎 On iOS, fixes Alarmees being duplicated in some cases, by keeping only one trigger.

## [1.4.1] - December 21, 2024
- Uses KMPKit library instead of deprecated kmp-common.
- 🤖 On Android, you can now customize the notification icon color and drawable for all notifications in your app or on a per-notification basis.

## [1.4.0] - December 17, 2024
- It is now possible to schedule a repeating Alarmee with a specific `Duration` interval, using [RepeatInterval.Custom](https://github.com/Tweener/alarmee/blob/main/alarmee/src/commonMain/kotlin/com/tweener/alarmee/RepeatInterval.kt#L18). See [sample](https://github.com/Tweener/alarmee/blob/main/sample/composeApp/src/commonMain/kotlin/com/tweener/alarmee/sample/App.kt#L78-L95).
- **`[BREAKING]`** `RepeatInterval` is now a sealed class instead of an enum class.

## [1.3.4] - December 16, 2024
- 🍎 On iOS, add granularity for seconds when scheduling a repeating Alarmee.
- Sample: Update iOS sample to show notifications when the app is in foreground (See [documentation](https://developer.apple.com/documentation/usernotifications/scheduling-a-notification-locally-from-your-app#overview)).

## [1.3.3] - November 29, 2024
- Added support for JVM, JS, and WasmJS targets.
> [!WARNING]
> Proper implementations for these targets are pending future updates. This is added so projects targeting these platforms can use Alarmee.

## [1.3.2] - November 27, 2024
- Removed core library desugaring.

## [1.3.1] - November 27, 2024
- Add log when an alarm is scheduled in the past and rescheduled it to tomorrow.
- 🤖 On Android, ensure specified channel ID for Alarmee exists before scheduling the Alarmee to prevent lost notifications.
- 🍎 On iOS, add granularity for seconds when scheduling an Alarmee.

## [1.3.0] - November 26, 2024
- **`[BREAKING]`** `Alarmee#schedule(...)` and `Alarmee#cancel(...)` are not Composable functions anymore.
- 🤖 On Android, it is now possible to configure a channel's importance and a notification's priority.
- 🤖 On Android, if not using Compose Multiplatform, a `Context` must be passed to the `AlarmeeSchedulerAndroid` instance.

## [1.2.0] - November 20, 4
- **`[BREAKING]`** `AlarmeePlatformConfiguration.Android` and `AlarmeePlatformConfiguration.Ios` have been replaced respectively by `AlarmeeAndroidPlatformConfiguration` and `AlarmeeIosPlatformConfiguration`. Details [here](https://github.com/Tweener/alarmee?tab=readme-ov-file#platform-configurations).
- Introduced repeating alarms with intervals: hourly, daily, weekly, monthly, and yearly.
- 🤖 On Android, you can create more than one notification channel. See [AlarmeeAndroidPlatformConfiguration](https://github.com/Tweener/alarmee/blob/main/alarmee/src/androidMain/kotlin/com/tweener/alarmee/configuration/AlarmeeAndroidPlatformConfiguration.kt)

## [1.1.0] - November 14, 2024
- **`[BREAKING]`** Creating an instance of `AlarmeeScheduler` now requires to pass a `AlarmeePlatformConfiguration` which is platform dependent. Details [here](https://github.com/Tweener/alarmee?tab=readme-ov-file#platform-configurations).
- **Alarmee is now compatible with Compose Multiplatform! 🚀** Create an instance of `AlarmeeScheduler` and remember it during recomposition. Details [here](https://github.com/Tweener/alarmee?tab=readme-ov-file#platform-configurations).
- Added one-off alarms that trigger at a specific date and time.

## [1.0.1] - November 13, 2024
- Napier dependency has been removed and logs are now using println.

## [1.0.0] - November 7, 2024

### 🚀 Initial Release

The first official release of **Alarmee**.

#### Features
  - Unified API for scheduling alarms and notifications across Android and iOS.
  - Support for one-off alarms.
  - Extensible configuration for platform-specific settings.
