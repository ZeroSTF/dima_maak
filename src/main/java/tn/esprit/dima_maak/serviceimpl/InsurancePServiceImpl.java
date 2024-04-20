package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.InsuranceP;
import tn.esprit.dima_maak.repositories.InsurancePRepository;
import tn.esprit.dima_maak.services.IInsurancePService;

import java.text.DecimalFormat;
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

    @Override
    public Long gettotalinsurancepackscount() {
        return iprepo.count();
    }

    @Override
    public long countInsurancePacksByType(IType type) {
        return iprepo.countInsurancesByPackType(type);
    }

    @Override
    public String calculatePercentageByType(IType type) {
        Long totalpacks = gettotalinsurancepackscount();
        if (totalpacks == 0) {
            return "0.00%"; // Return a default formatted percentage if totalpacks is 0
        }
        long packsOfType = countInsurancePacksByType(type);
        double percentage = (double) packsOfType / totalpacks * 100.0;
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(percentage / 100.0);

    }


}
