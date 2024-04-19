package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.Premium;

import java.util.List;

public interface IPremiumService {
    public String retardamount(Long idins);
    public Premium payment(Long idPremium);
    Premium addPremium (Premium p);
    Premium getPremiumById (Long id);
    void deletePremium (Long id);
    List<Premium> getALL();
    Premium updatePremium (Premium p);
    Long getTotalPremiumCount();
    Long countpremiumsbystatus (boolean satus);
    List<Premium> findallpremiumsofuser (Long iduser);
    List<Premium> finduserpremiumsbystatus(Long iduser, boolean status);
    Premium assignpremiumtoinsurance (Long idpremium,Long idinsurance );

}
