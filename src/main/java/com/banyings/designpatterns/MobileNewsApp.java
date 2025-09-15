package com.banyings.designpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * Another Concrete Observer - Mobile App
 */
public class MobileNewsApp implements NewsObserver {
    private String appName;
    private List<String> notifications;
    
    public MobileNewsApp(String appName) {
        this.appName = appName;
        this.notifications = new ArrayList<>();
    }
    
    @Override
    public void update(String headline, String content) {
        notifications.add(headline);
        System.out.printf("[%s] 📱 Push Notification: %s%n", appName, headline);
    }
    
    public String getAppName() { return appName; }
    public List<String> getNotifications() { return notifications; }
}