package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.Premium;

import java.util.List;

public interface IPremiumService {
    Premium addPremium (Premium p);
    Premium getPremiumById (Long id);
    void deletePremium (Long id);
    List<Premium> getALL();
    Premium updatePremium (Premium p);
}
