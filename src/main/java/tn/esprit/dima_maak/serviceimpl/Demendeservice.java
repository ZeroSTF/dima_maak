package tn.esprit.dima_maak.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.IAssetRepository;
import tn.esprit.dima_maak.repositories.IDemandeRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IDemande;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class Demendeservice implements IDemande {

    @Autowired
    IAssetRepository assetService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    IDemandeRepository iDemandeRepository;
    @Autowired
    EmailService emailService;


    @Override
    public ResponseEntity<?> createDemande(Demande demande, Long userId, Long assetId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Asset asset = assetService.findById(assetId).orElse(null);
        if (asset == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset not found");
        }
        Demande existingDemand = iDemandeRepository.findByUserAndAsset(user, asset);
        if (existingDemand != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has already made a demand for this asset");
        }
        demande.setUser(user);
        demande.setAsset(asset);

        Demande savedDemande = iDemandeRepository.save(demande);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDemande);
    }


    public float calculateMonthlyPayment(Demande demande) {
        if (demande == null) {
            throw new IllegalArgumentException("La demande est nulle.");
        }



                float totalAmount = demande.getAsset().getPrice() ;
                float montantparmonth = totalAmount/12;
                float monthlyPaymentPercentage = 0.25f;

        float     monthlyPayment = montantparmonth * monthlyPaymentPercentage;



        return Math.round(monthlyPayment * 100) / 100.0f;
    }

    @Override
    public Demande acceptdemande( LStatus status, Long iddemende) {
        Demande demande1=iDemandeRepository.findById(iddemende).orElse(null);
        demande1.setStatus(status);
        float cal =calculateMonthlyPayment(demande1);
        String title="";
        String body="";
        if (status.equals(LStatus.Offer)){
             title="Status is "+ demande1.getStatus();
             body = "thank you ,your  MonthlyPayment is : " + cal  + " click here true for confirm http://localhost:4200/confirme/" +iddemende  ;
        }else if (status.equals(LStatus.Accepted)){
             title="Status is "+ demande1.getStatus();
              body = "thank you " ;
        }

        sendemail(demande1.getUser().getEmail(),title,body);
        return iDemandeRepository.save(demande1);

    }
    private void sendemail(String userEmail, String title, String body) {
        new Thread(() -> {
            try {
                emailService.sendEmail(userEmail, title, body);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }
    @Override
    public Demande getdemandeById(Long iddemande) {
        Demande demande = iDemandeRepository.findById(iddemande).get();
        return demande;
    }

    @Override
    public Demande updateAsset( Long iddemande,Demande demande) {
        Demande demande1=iDemandeRepository.findById(iddemande).orElse(null);
        demande1.setRequestDate(demande.getRequestDate());
        demande1.setStatus(demande.getStatus());
        return demande1;
    }

    @Override
    public ResponseEntity<Void> deleteAsset( Long iddemande) {
        iDemandeRepository.deleteById(iddemande);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public Iterable<Demande> getAllAssets() {
        Iterable<Demande> demandeList = iDemandeRepository.findAll();
        return iDemandeRepository.findAll();
    }
    @Override
    public Iterable<Demande> getAllAssetsuser(Long iduser) {
        User user = userRepository.findById(iduser).orElse(null);
        Iterable<Demande> demandeList = iDemandeRepository.findByUser(user);
        return demandeList;
    }
    @Override
    public User getuserfromdemande(Long iddemende) {
        Demande demandeList = iDemandeRepository.findById(iddemende).orElse(null);
        User user=userRepository.findUserByDemandeList(demandeList);
        return demandeList.getUser();
    }
    public Asset getassetofdemande( Long iddemende) {
        Demande demande= iDemandeRepository.findById(iddemende).orElse(null);
        Asset asset = assetService.findAssetByDemandeList(demande);
        return asset;
    }
    @Override
    public User getUserOfDemande(Long iddemende) {
        Demande demande = iDemandeRepository.findById(iddemende).orElse(null);
        if (demande != null) {
            User user = demande.getUser();
            return user;
        } else {
            return null;
        }
    }

}
