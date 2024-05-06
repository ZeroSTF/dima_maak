package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.dima_maak.entities.Location;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
