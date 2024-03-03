package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Claim;

public interface ClaimRepository extends CrudRepository<Claim,Long> {
}
