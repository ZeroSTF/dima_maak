package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.Leasing;

import java.util.Optional;

public interface ILeasingService {
    Leasing createLeasing(Leasing l );
    Optional<Leasing> getLeasingById(Long leaseid);
    Leasing updateLeasing(Leasing updatedLeasing);
    void deleteLeasingById(Long leaseid);
}
