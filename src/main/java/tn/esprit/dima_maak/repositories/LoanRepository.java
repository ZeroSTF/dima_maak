package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan,Long> {
    List<Loan> findByUserId(Long userId);

}