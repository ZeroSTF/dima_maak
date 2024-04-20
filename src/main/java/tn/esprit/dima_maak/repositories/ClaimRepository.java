package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.CStatus;
import tn.esprit.dima_maak.entities.Claim;

@Repository
public interface ClaimRepository extends CrudRepository<Claim,Long> {
    Long countByStatus (CStatus status);
}
