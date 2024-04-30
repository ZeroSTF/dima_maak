package tn.esprit.dima_maak.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.query.Param;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.InsuranceP;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IInsuranceService {
    Insurance addInsurance (Insurance i);
    Insurance getInsuranceById (Long id);
    void deleteInsurance (Long id);
    List<Insurance> getALL();
    Insurance updateInsurance (Insurance i);
     Insurance addinsuranceandassigntouser (Insurance insurance,Long iduser);
     Insurance assigninsurancetouser(Long idinsurance,Long iduser);
     Insurance assinsuranceandassigntoinsurancepack (Insurance insurance, Long idinsuranceP);
     List<Insurance> retrieveinsurancebypacktype(IType insurancePType);
      Long gettotalinsurance();
    //public Map<String, Long> getInsuranceTypeDistribution();
     Insurance assigninsurancetoinsurancepack (Long idinsurance, Long idinsurancepack);
    Long countInsurancesByPackType ( IType packtype);
    String calculatePercentageByType (IType packtype);
    float findTotalCoverageAmountByPackType(IType packType);
    public Insurance createInsurance(Long insurancePackId,Long iduser);
    public Insurance updatedInsurance(Long insuranceId);
}
