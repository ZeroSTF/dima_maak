package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.ChargeRequest;

@Repository
public interface ChargeRepo extends JpaRepository<ChargeRequest,Long> {
}
