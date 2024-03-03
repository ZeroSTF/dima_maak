package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.InsuranceP;
import tn.esprit.dima_maak.repositories.InsurancePRepository;
import tn.esprit.dima_maak.services.IInsurancePService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class InsurancePServiceImpl implements IInsurancePService {

    private InsurancePRepository iprepo;

    @Override
    public InsuranceP addInsuranceP(InsuranceP ip) {
        return iprepo.save(ip);
    }

    @Override
    public InsuranceP getInsurancePById(Long idp) {
        return iprepo.findById(idp).orElse(null);
    }

    @Override
    public void deleteInsuranceP(Long idp) {
        iprepo.deleteById(idp);

    }

    @Override
    public List<InsuranceP> getALL() {
        return (List<InsuranceP>) iprepo.findAll();
    }

    @Override
    public InsuranceP updateInsuranceP(InsuranceP iP) {
        return iprepo.save(iP);
    }
}
