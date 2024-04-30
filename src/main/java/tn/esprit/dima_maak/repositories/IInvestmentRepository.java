package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Investment;

import java.util.List;

@Repository
public interface IInvestmentRepository extends CrudRepository<Investment ,Long> {

   /* @Query("SELECT i FROM Investment i JOIN FETCH i.returns")
    List<Investment> getAllInvestmentsWithReturns();*/
}