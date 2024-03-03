package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.services.IInsuranceService;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InsuranceServiceImpl implements IInsuranceService {

private InsuranceRepository inrep;
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


}
