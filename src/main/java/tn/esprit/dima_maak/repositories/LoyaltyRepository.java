package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.dima_maak.entities.Loyalty;
import tn.esprit.dima_maak.entities.Notification;

public interface LoyaltyRepository extends JpaRepository<Loyalty, Long> {
}
