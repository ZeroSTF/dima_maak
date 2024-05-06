package tn.esprit.dima_maak.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.CStatus;
import tn.esprit.dima_maak.entities.Claim;
import tn.esprit.dima_maak.entities.Insurance;

import java.io.IOException;
import java.util.List;

public interface IClaimService {
     String saveImageToStorage(String uploadDirectory, MultipartFile imageFile ) throws IOException;

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
