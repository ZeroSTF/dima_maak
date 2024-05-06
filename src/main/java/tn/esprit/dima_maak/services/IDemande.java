package tn.esprit.dima_maak.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.Demande;
import tn.esprit.dima_maak.entities.LStatus;
import tn.esprit.dima_maak.entities.User;

import javax.mail.MessagingException;

@Repository
public interface IDemande {
    public ResponseEntity<?> createDemande(Demande demande, Long userId, Long assetId) ;

    public Demande acceptdemande(LStatus status, Long iddemende) ;
    public Iterable<Demande> getAllAssetsuser(Long iduser);

    public Demande getdemandeById(Long iddemande) ;

    public Demande updateAsset( Long iddemande,Demande demande) ;


    public ResponseEntity<Void> deleteAsset(Long iddemande) ;


    public Iterable<Demande> getAllAssets();

    public User getuserfromdemande(Long iddemende) ;
    public Asset getassetofdemande( Long iddemende) ;

    public User getUserOfDemande(Long iddemende) ;
}
