package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.Leasing;

import java.util.Optional;

public interface ILeasingService {
    public Leasing createLeasing(Leasing l,Long iduser,Long iddemande);
    Optional<Leasing> getLeasingById(Long leaseid);
    public Iterable<Leasing> leasingList ();
    public Leasing updateLeasing(Leasing l,Long id) ;
    void deleteLeasingById(Long leaseid);

   String assignUserToLeasing(Long id, Long leaseid) ;
    float calculateAnnualInterestRate(Leasing leasing);
    public float calculateLatePaymentPercentage(Leasing leasing);

}
