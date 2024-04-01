package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.CStatus;
import tn.esprit.dima_maak.entities.Claim;
import tn.esprit.dima_maak.entities.Insurance;

import java.util.List;

public interface IClaimService {
    Claim addClaim (Claim c);
    Claim getClaimbyid (Long id);
    void deleteClaim (Long id);
    List<Claim> getALL();
    Claim updateclaim (Claim c);
   // String analyzeRisk (Long id);
    Claim addclaimandassigntoinsurance(Claim c,Long idinsurance);
    Long getTotalClaimCount();
    Long  countclaimsbystatus (CStatus status);

}
