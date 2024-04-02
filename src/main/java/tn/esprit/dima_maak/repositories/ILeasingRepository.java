package tn.esprit.dima_maak.repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Leasing;

import java.util.List;
import java.util.Optional;

public interface ILeasingRepository extends CrudRepository<Leasing,Long> {


    public Optional<Leasing> findById(Long  leaseid);
   /*  @Modifying
    @Transactional
    @Query("UPDATE Leasing l SET l.creditScore = :creditScore WHERE l.user.id = :userId")
    void updateCreditScore(Long userId, int creditScore);

    @Query("SELECT l.creditScore FROM Leasing l WHERE l.user.id = :userId")
    int getCreditScore(Long userId);   */
}



