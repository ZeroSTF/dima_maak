package tn.esprit.dima_maak.serviceimpl;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.InsuranceP;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.InsurancePRepository;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IInsuranceService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InsuranceServiceImpl implements IInsuranceService {

private InsuranceRepository inrep;
private UserRepository userrep;
private InsurancePRepository inPrep;
    @Override
    public Insurance addInsurance(Insurance i) {
        return inrep.save(i);
    }

    @Override
    public Insurance getInsuranceById(Long id) {
        return inrep.findById(id).orElse(null);
    }

    @Override
    public void deleteInsurance(Long id) {
        inrep.deleteById(id);

    }

    @Override
    public List<Insurance> getALL() {

            return(List<Insurance>) inrep.findAll();


    }


    @Override
    public Insurance updateInsurance(Insurance i) {
        return inrep.save(i);
    }

    @Override
    public Insurance addinsuranceandassigntouser(Insurance insurance, Long iduser) {
        inrep.save(insurance);
        User user = userrep.findById(iduser).orElse(null);
        insurance.setUser(user);
        return  inrep.save(insurance);


    }

    @Override
    public Insurance assigninsurancetouser(Long idinsurance, Long iduser) {
        Insurance i=inrep.findById(idinsurance).orElse(null);
        User u=userrep.findById(iduser).orElse(null);
        i.setUser(u);
        return inrep.save(i);
    }

    @Override
    public Insurance assinsuranceandassigntoinsurancepack(Insurance insurance, Long idinsuranceP) {
        inrep.save(insurance);
        InsuranceP inp = inPrep.findById(idinsuranceP).orElse(null);
        insurance.setInsuranceP(inp);
        return inrep.save(insurance);
    }

    @Override
    public List<Insurance> retrieveinsurancebypacktype(IType insurancePType) {
        return inrep.retrieveinsurancebypacktype(insurancePType);
    }

    @Override
    public Long gettotalinsurance() {
        return inrep.count();
    }

   /* @Override
    public Map<String, Long> getInsuranceTypeDistribution() {
        return inrep.getInsuranceTypeDistribution();
    }*/

    @Override
    public Insurance assigninsurancetoinsurancepack(Long idinsurance, Long idinsurancepack) {
        InsuranceP inp = inPrep.findById(idinsurancepack).orElse(null);
        Insurance i = inrep.findById(idinsurance).orElse(null);
        i.setInsuranceP(inp);
        return inrep.save(i);
    }

    @Override
    public Long countInsurancesByPackType(IType packtype) {
        return inrep.countinsurancebypacktype(packtype);
    }

    @Override
    public String calculatePercentageByType(IType packtype) {
        Long totalinsurances = gettotalinsurance();
        if(totalinsurances == 0)
        {
            return "0.00%";
        }
        Long insurancesbypacktype = countInsurancesByPackType(packtype);
        double percentage = (double) insurancesbypacktype/totalinsurances *100.0;
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(percentage/100.0);
    }

    @Override
    public float findTotalCoverageAmountByPackType(IType packType) {
        return inrep.findTotalCoverageAmountByPackType(packType);
    }

    @Override
    public Insurance createInsurance(Long insurancePackId, Long iduser) {
        return null;
    }


}
