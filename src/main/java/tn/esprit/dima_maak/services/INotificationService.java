package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Notification;
import tn.esprit.dima_maak.entities.User;

import java.util.List;

public interface INotificationService {
    public List<Notification> retrieveAllNotifications();
    public Notification retrieveNotification(Long id);
    public Notification addNotification(Notification notification);
    public void removeNotification(Long id);
    public Notification modifyNotification(Notification notification);
    void sendLowBalanceNotification(User user, float threshold);
    void sendProfileEditNotification(User user);
    public List<Notification> getUnread(User user);
}
