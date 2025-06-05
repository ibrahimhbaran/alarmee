package com.tweener.alarmee.sample

import com.tweener.alarmee.PushNotificationServiceRegistry

/**
 * @author Vivien Mahe
 * @since 05/06/2025
 */
class AlarmeeHelper {

    fun onRemoteMessageReceived(userInfo: Map<Any?, *>?) {
        val parsed = userInfo
            ?.mapNotNull { (key, value) ->
                val k = key?.toString()
                val v = value?.toString()
                if (k != null && v != null) k to v else null
            }
            ?.toMap()
            ?: emptyMap()

        PushNotificationServiceRegistry.get()?.onMessageReceived(data = parsed)
    }
}
