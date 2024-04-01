package tn.esprit.dima_maak.serviceimpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Notification;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.NotificationRepository;
import tn.esprit.dima_maak.services.INotificationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final NotificationRepository notificationRepository;
    @Override
    public List<Notification> retrieveAllNotifications() {return notificationRepository.findAll();}

    @Override
    public Notification retrieveNotification(Long id) {
        if (notificationRepository.findById(id).isPresent()){
            Notification notification = notificationRepository.findById(id).get();
            notification.setStatus(true);
            notificationRepository.save(notification);
            return notification;
        }else {
            throw new EntityNotFoundException("Notification not found with id: " + id);
        }
    }

    @Override
    public Notification addNotification(Notification notification) {
        notification.setStatus(false);
        return notificationRepository.save(notification);
    }

    @Override
    public void removeNotification(Long id) {notificationRepository.deleteById(id);}

    @Override
    public Notification modifyNotification(Notification notification) {
        Optional<Notification> existingUser = notificationRepository.findById(notification.getId());
        if (existingUser.isPresent()) {
            return notificationRepository.save(notification);
        } else {
            throw new EntityNotFoundException("Notification not found with id: " + notification.getId());
        }
    }
}
