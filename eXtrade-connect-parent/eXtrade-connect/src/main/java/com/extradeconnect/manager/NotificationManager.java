package com.extradeconnect.manager;

import com.extradeconnect.beans.notification.MailNotification;
import com.extradeconnect.beans.notification.Notification;

public interface NotificationManager {

    void textNotification(Notification notification);
    void mailNotification(MailNotification mailNotification);

}
