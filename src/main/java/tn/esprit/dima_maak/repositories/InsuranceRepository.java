package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Insurance;

import java.time.LocalDate;

public interface InsuranceRepository extends CrudRepository<Insurance,Long> {
    Insurance findByStartDate (LocalDate date);
}
