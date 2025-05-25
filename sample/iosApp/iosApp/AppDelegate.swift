//
//  AppDelegate.swift
//  iosApp
//
//  Created by Vivien Mahé on 27/11/2024.
//

import SwiftUI
import composeApp

@MainActor
class AppDelegate : NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        UNUserNotificationCenter.current().delegate = self
        return true
    }
    
    nonisolated func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([.banner, .list, .badge, .sound])
    }
    
    nonisolated func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        let userInfo = response.notification.request.content.userInfo
        
        print("userInfo: \(userInfo)")

        if let value = userInfo["deepLinkUri"] as? String {
            // Check for the deep link URI here, which is only present if it was provided in the Alarmee(deepLinkUri: ...) creation.
        }

        completionHandler()
    }
}
