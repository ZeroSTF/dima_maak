package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.InsuranceP;

import java.time.LocalDate;
import java.util.List;

public interface IInsuranceService {
    Insurance addInsurance (Insurance i);
    Insurance getInsuranceById (Long id);
    void deleteInsurance (Long id);
    List<Insurance> getALL();
    Insurance updateInsurance (Insurance i);


}
