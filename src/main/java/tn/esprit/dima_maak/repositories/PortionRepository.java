package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Loan;
import tn.esprit.dima_maak.entities.Portion;

import java.util.List;

public interface PortionRepository extends CrudRepository<Portion,Long> {
    List<Portion> findByLoan(Loan loan);

}