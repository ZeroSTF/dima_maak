package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.Premium;

import java.util.List;

@Repository
public interface PremiumRepository extends CrudRepository<Premium,Long> {
    Long countByStatus (boolean satus);

    List<Premium> findByInsuranceUserId(Long userid);
    List<Premium> findByInsuranceUserIdAndStatus(Long userid,boolean status);

    List<Premium> findPremiumByInsurance(Insurance insurance);
}
