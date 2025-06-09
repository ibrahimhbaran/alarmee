package com.tweener.alarmee

/**
 * Platform-specific extension of [AlarmeeService] for mobile platforms (Android and iOS).
 *
 * This interface adds support for remote push notifications via [PushNotificationService].
 * It is only available on `mobileMain`, which includes Android and iOS targets.
 *
 * To access push notifications from the shared [AlarmeeService], you can safely cast it:
 *
 * ```kotlin
 * val pushService = (alarmeeService as? MobileAlarmeeService)?.push
 * ```
 *
 * This allows platform-specific code (e.g., Firebase or APNs handlers) to trigger custom behavior
 * when a remote push message is received.
 *
 * @author Vivien Mahe
 * @since 05/06/2025
 */
interface MobileAlarmeeService : AlarmeeService {

    /**
     * Service for handling remote push notifications.
     *
     * Only available on Android and iOS targets.
     */
    val push: PushNotificationService
}
