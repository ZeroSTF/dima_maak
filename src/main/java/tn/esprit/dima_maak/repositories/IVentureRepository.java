package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Venture;
@Repository
public interface IVentureRepository extends JpaRepository<Venture, Long> {


    @Query("SELECT SUM(i.purchasedShares) FROM Investment i WHERE i.venture.idV = :idV")
    Long getTotalPurchasedSharesForVenture(@Param("idV") Long idV);

    @Query("SELECT SUM(i.amount) FROM Investment i WHERE i.venture.idV = :idV")
    Long getTotalAmountForVenture(@Param("idV") Long idV);

}