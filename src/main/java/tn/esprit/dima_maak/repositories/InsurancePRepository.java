package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.InsuranceP;

import java.util.List;

public interface InsurancePRepository extends CrudRepository<InsuranceP,Long> {
   // List<Insurance> findByCoverageAmount (Float coverageamount);
   // List<Insurance> findByType (IType type);

}
