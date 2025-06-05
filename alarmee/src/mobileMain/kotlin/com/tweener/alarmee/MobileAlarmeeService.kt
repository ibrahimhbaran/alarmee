package com.tweener.alarmee

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
interface MobileAlarmeeService : AlarmeeService {
    val push: PushNotificationService
}
