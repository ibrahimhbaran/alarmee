# Changelog

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
