package tn.esprit.dima_maak.repositories;


import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Leasing;

import java.time.LocalDate;
import java.util.Optional;

public interface ILeasingRepository extends CrudRepository<Leasing,Long> {

   Iterable<Leasing> findLeasingByDemande_Id(Long id);

    public Optional<Leasing> findById(Long  leaseid);

    Iterable<Leasing> findLeasingByEnddateAfter(LocalDate now);
   /*  @Modifying
    @Transactional
    @Query("UPDATE Leasing l SET l.creditScore = :creditScore WHERE l.user.id = :userId")
    void updateCreditScore(Long userId, int creditScore);

    @Query("SELECT l.creditScore FROM Leasing l WHERE l.user.id = :userId")
    int getCreditScore(Long userId);   */
}



