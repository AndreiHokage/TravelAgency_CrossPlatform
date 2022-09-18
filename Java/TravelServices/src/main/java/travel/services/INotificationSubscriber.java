package travel.services;

import travel.model.notification.Notification;

public interface INotificationSubscriber {
    void notificationReceived(Notification notif);
}
