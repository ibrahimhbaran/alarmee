package com.tweener.alarmee.sample

import com.tweener.alarmee.configuration.AlarmeeIosPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

/**
 * @author Vivien Mahe
 * @since 26/11/2024
 */

private val alarmeePlatformConfiguration = AlarmeeIosPlatformConfiguration

actual fun createAlarmeePlatformConfiguration(): AlarmeePlatformConfiguration = alarmeePlatformConfiguration
