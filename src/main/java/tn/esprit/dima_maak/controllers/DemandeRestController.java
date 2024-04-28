package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.IAssetRepository;
import tn.esprit.dima_maak.repositories.IDemandeRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.serviceimpl.AssetServiceImpl;
import tn.esprit.dima_maak.serviceimpl.EmailService;
import tn.esprit.dima_maak.services.ILeasingService;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/demande")

public class DemandeRestController {

    @Autowired
    IAssetRepository assetService;

    @Autowired UserRepository userRepository;
    @Autowired
     IDemandeRepository iDemandeRepository;
    @Autowired
    EmailService emailService;


    @PostMapping("/save")
    public Demande createdemande(@RequestBody Demande demande, @RequestParam("id") Long id, @RequestParam("idasset") Long idasset) {
        User user= userRepository.findById(id).orElse(null);
        Asset asset=assetService.findById(idasset).orElse(null);
        demande.setUser(user);
        demande.setAsset(asset);
        return iDemandeRepository.save(demande);
    }
    @PostMapping("/status")
    public Demande acceptdemande(@RequestParam("status") LStatus status, @RequestParam("iddemende") Long iddemende) {
        Demande demande1=iDemandeRepository.findById(iddemende).orElse(null);
        demande1.setStatus(status);
        String title="Status is "+ demande1.getStatus();
        String  body = "thank you ";
        try {
            emailService.sendEmail(demande1.getUser().getEmail(), title,body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return iDemandeRepository.save(demande1);

    }

    @GetMapping("/getbyid")
    public Demande getdemandeById(@RequestParam("iddemande") Long iddemande) {
        Optional<Demande> demande = iDemandeRepository.findById(iddemande);
        return demande.get();
    }

    @PutMapping("/update")
    public Demande updateAsset(@RequestParam("iddemande") Long iddemande, @RequestBody Demande demande) {
       Demande demande1=iDemandeRepository.findById(iddemande).orElse(null);
       demande1.setRequestDate(demande.getRequestDate());
       demande1.setStatus(demande.getStatus());
        return demande1;
    }

    @DeleteMapping("/delete/{iddemande}")
    public ResponseEntity<Void> deleteAsset(@PathVariable("iddemande") Long iddemande) {
        iDemandeRepository.deleteById(iddemande);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public Iterable<Demande> getAllAssets() {
        Iterable<Demande> demandeList = iDemandeRepository.findAll();
        return iDemandeRepository.findAll();
    }
    @GetMapping("/getuserofdemande")
    public User getuserfromdemande(@RequestParam("iddemende") Long iddemende) {
        Demande demandeList = iDemandeRepository.findById(iddemende).orElse(null);
        User user=userRepository.findUserByDemandeList(demandeList);
        return user;
    }
    @GetMapping("/getassetofdemande")
    public Asset getassetofdemande(@RequestParam("iddemende") Long iddemende) {
        Demande demande= iDemandeRepository.findById(iddemende).orElse(null);
        Asset asset = assetService.findAssetByDemandeList(demande);
        return asset;
    }
    @GetMapping("/getuserofdemande0")
    public User getUserOfDemande(@RequestParam("iddemende") Long iddemende) {
        Demande demande = iDemandeRepository.findById(iddemende).orElse(null);
        if (demande != null) {
            User user = demande.getUser();
            return user;
        } else {
            // Handle the case where the demand with the given id doesn't exist
            return null; // or throw an exception
        }
    }




}








