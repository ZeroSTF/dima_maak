package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.InsuranceP;

import java.util.List;
@Repository
public interface InsurancePRepository extends CrudRepository<InsuranceP,Long> {
   // List<Insurance> findByCoverageAmount (Float coverageamount);
   // List<Insurance> findByType (IType type);

    @Query("SELECT COUNT(ip) FROM InsuranceP ip WHERE ip.type = :type")
    long countInsurancesByPackType(@Param("type") IType type);

    //InsuranceP findByType(IType type);
    @Query("SELECT ip FROM InsuranceP ip WHERE ip.type = :type")
    List<InsuranceP> findByType(@Param("type") IType type);

}

