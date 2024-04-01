package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.Premium;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.repositories.PremiumRepository;
import tn.esprit.dima_maak.services.IPremiumService;

import java.util.List;

@Service
@AllArgsConstructor

public class PremiumServiceImpl implements IPremiumService {

    private PremiumRepository premrep;
    private InsuranceRepository insurancerep;
    @Override
    public Premium addPremium(Premium p) {
        return premrep.save(p);
    }

    @Override
    public Premium getPremiumById(Long id) {
        return premrep.findById(id).orElse(null);
    }

    @Override
    public void deletePremium(Long id) {
        premrep.deleteById(id);

    }

    @Override
    public List<Premium> getALL() {
        return (List<Premium>) premrep.findAll();
    }

    @Override
    public Premium updatePremium(Premium p) {
        return premrep.save(p);
    }

    @Override
    public Long getTotalPremiumCount() {
        return premrep.count();
    }

    @Override
    public Long countpremiumsbystatus(boolean status) {
        return premrep.countByStatus(status);
    }

    @Override
    public List<Premium> findallpremiumsofuser(Long iduser) {
        return premrep.findByInsuranceUserId(iduser);
    }

    @Override
    public List<Premium> finduserpremiumsbystatus(Long iduser, boolean status) {
        return premrep.findByInsuranceUserIdAndStatus(iduser, status);
    }

    @Override
    public Premium assignpremiumtoinsurance(Long idpremium, Long idinsurance) {
        Premium p = premrep.findById(idpremium).orElse(null);
        Insurance i=insurancerep.findById(idinsurance).orElse(null);
        p.setInsurance(i);
        return premrep.save(p);

    }
}
