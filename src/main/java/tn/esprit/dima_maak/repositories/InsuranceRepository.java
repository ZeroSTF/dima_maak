package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public interface InsuranceRepository extends CrudRepository<Insurance,Long> {
    Insurance findByStartDate (LocalDate date);

    @Query("select i from Insurance i where i.insuranceP.type=:typepack")
    List<Insurance> retrieveinsurancebypacktype(@Param("typepack") IType insurancePType);

   // @Query("select i.insuranceP.type, count (i) from Insurance i group by i.insuranceP.type")
   // Map<String, Long> getInsuranceTypeDistribution();

    @Query("select count(i) from Insurance i where i.insuranceP.type=:packtype ")
    Long countinsurancebypacktype (@Param("packtype") IType packtype);

    @Query("SELECT SUM(i.clientcoverageamount) FROM Insurance i WHERE i.insuranceP.type =:packtype")
    float findTotalCoverageAmountByPackType(@Param("packtype")IType packType);


    List<Insurance> findByUser(User user);
}
