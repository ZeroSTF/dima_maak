package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.Demande;
import tn.esprit.dima_maak.entities.User;

import java.util.Optional;
@Repository
public interface IDemandeRepository extends CrudRepository<Demande, Long> {


    Demande findByUserAndAsset(User user, Asset asset);

    Iterable<Demande> findByUser(User user);
}