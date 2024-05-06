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
import tn.esprit.dima_maak.serviceimpl.Demendeservice;
import tn.esprit.dima_maak.serviceimpl.EmailService;
import tn.esprit.dima_maak.services.ILeasingService;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/demande")
@CrossOrigin(origins = "*")
public class DemandeRestController {

    @Autowired
    IAssetRepository assetService;

    @Autowired UserRepository userRepository;
    @Autowired
    Demendeservice iDemandeRepository;
    @Autowired
    EmailService emailService;


    @PostMapping("/save")
    public ResponseEntity<?> createdemande(@RequestBody Demande demande, @RequestParam("id") Long id, @RequestParam("idasset") Long idasset) {

        return iDemandeRepository.createDemande(demande,id,idasset);
    }
    @PostMapping("/status")
    public Demande acceptdemande(@RequestParam("status") LStatus status, @RequestParam("iddemende") Long iddemende) {

        return iDemandeRepository.acceptdemande(status,iddemende);

    }
    @GetMapping("listdemande")
    public Iterable<Demande> getAllAssetsuser(Long iduser) {

        return iDemandeRepository.getAllAssetsuser(iduser);
    }

    @GetMapping("/getbyid")
    public Demande getdemandeById(@RequestParam("iddemande") Long iddemande) {
        return iDemandeRepository.getdemandeById(iddemande);
    }

    @PutMapping("/update")
    public Demande updateAsset(@RequestParam("iddemande") Long iddemande, @RequestBody Demande demande) {
     return iDemandeRepository.updateAsset(iddemande,demande);
    }

    @DeleteMapping("/delete/{iddemande}")
    public ResponseEntity<Void> deleteAsset(@PathVariable("iddemande") Long iddemande) {
        iDemandeRepository.deleteAsset(iddemande);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public Iterable<Demande> getAllAssets() {
       return iDemandeRepository.getAllAssets();
    }
    @GetMapping("/getuserofdemande")
    public User getuserfromdemande(@RequestParam("iddemende") Long iddemende) {

        return iDemandeRepository.getuserfromdemande(iddemende);
    }
    @GetMapping("/getassetofdemande")
    public Asset getassetofdemande(@RequestParam("iddemende") Long iddemende) {

        return iDemandeRepository.getassetofdemande(iddemende);
    }





}








