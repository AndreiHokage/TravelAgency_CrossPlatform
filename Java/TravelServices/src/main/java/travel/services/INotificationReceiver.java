package travel.services;

public interface INotificationReceiver {
    void start(INotificationSubscriber subscriber);
    void stop();
}
