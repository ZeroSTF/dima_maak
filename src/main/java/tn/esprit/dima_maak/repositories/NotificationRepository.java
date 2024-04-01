package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
