package tn.esprit.dima_maak.serviceimpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.NType;
import tn.esprit.dima_maak.entities.Notification;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.NotificationRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.INotificationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

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

    ///////////////Custom Services///////////////////:
    @Override
    public void sendLowBalanceNotification(User user, float threshold) {
        if (user.getBalance() < threshold) {
            Notification notification = new Notification(user, NType.Alert, "Your account balance is low. Please top up soon.");
            notificationRepository.save(notification);
        }
    }
    @Override
    public void sendProfileEditNotification(User user){
        List<User> admins = userRepository.findAdminUsers();
        admins.forEach(admin -> {
            Notification notification = new Notification(admin, NType.Alert, "User "+user.getSurname()+" "+user.getName()+" has edited is profile.");
        });
    }
    @Override
    public List<Notification> getUnread(User user){
        return notificationRepository.findByUserAndStatus(user,false);
    }

    @Override
    public List<Notification> getByUser(User user){return notificationRepository.findNotificationsByUser(user);}
}
