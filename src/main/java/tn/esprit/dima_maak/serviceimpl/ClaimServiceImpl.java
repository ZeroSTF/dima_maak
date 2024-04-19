package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.CStatus;
import tn.esprit.dima_maak.entities.Claim;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.ClaimRepository;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IClaimService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor

public class ClaimServiceImpl implements IClaimService {

    private ClaimRepository claimrep;
    private UserRepository userrep;
    private InsuranceRepository inrep;
    @Override
    public Claim addClaim(Claim c) {
        return claimrep.save(c);
    }

    @Override
    public Claim getClaimbyid(Long id) {
        return claimrep.findById(id).orElse(null);
    }

    @Override
    public void deleteClaim(Long id) {
        claimrep.deleteById(id);

    }

    @Override
    public List<Claim> getALL() {
        return (List<Claim>) claimrep.findAll();
    }

    @Override
    public Claim updateclaim(Claim c) {
        return claimrep.save(c);
    }

    @Override
    public Claim addclaimandassigntoinsurance(Claim c, Long idinsurance) {
        claimrep.save(c);
        Insurance insurance = inrep.findById(idinsurance).orElse(null);
        c.setInsurance(insurance);
        return claimrep.save(c);
    }

    @Override
    public Long getTotalClaimCount() {
        return claimrep.count();
    }

    @Override
    public Long countclaimsbystatus(CStatus status) {
        return claimrep.countByStatus(status);
    }

   /* @Override
    public String analyzeRisk(Long id) {

        User user = userrep.findById(id).orElse(null);
        List<Claim> previousClaims = claimrep.findById(id);


        float riskScore = calculateRiskScore(user, previousClaims);


        String recommendations = generateRecommendations(riskScore);


        return "UserId: " + id + ", Risk Score: " + riskScore + ", Recommendations: " + recommendations;
    }


    private float calculateRiskScore(User user, List<Claim> previousClaims) {
        float riskScore = 0.0f;

        LocalDate currentDate = LocalDate.now();
       int age= Period.between(user.getBirthDate(), currentDate).getYears();

        if (age > 50) {
            riskScore += 0.2;
        }


        String profession = user.getJob();
        if (profession.equals("Pilote") || profession.equals("Médecin")) {
            riskScore += 0.3;
        }


        int claimCount = previousClaims.size();
        if (claimCount > 3) {
            riskScore += 0.5;
        }

        return riskScore;
    }





    private String generateRecommendations(float riskScore) {
        String recommendations = "";

        if (riskScore > 0.5) {
            recommendations = "Nous vous recommandons de souscrire une couverture d'assurance plus étendue pour mieux vous protéger.";
        } else {
            recommendations = "Votre risque est actuellement sous contrôle, mais nous vous recommandons de rester vigilant et de surveiller vos activités.";
        }

        return recommendations;
    }*/

    // Save image in a local directory
    @Override
    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println(filePath);
        return filePath.toString();
    }


}
