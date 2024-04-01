package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.InsuranceP;

import java.util.List;

public interface IInsurancePService {
    InsuranceP addInsuranceP (InsuranceP ip);
    InsuranceP getInsurancePById (Long idp);
    void deleteInsuranceP (Long idp);
    List<InsuranceP> getALL();
    InsuranceP updateInsuranceP (InsuranceP iP);
    Long gettotalinsurancepackscount();
    long countInsurancePacksByType(IType type);
    String calculatePercentageByType(IType type);
}
