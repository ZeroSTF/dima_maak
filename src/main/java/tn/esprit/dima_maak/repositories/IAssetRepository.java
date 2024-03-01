package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Asset;

public interface IAssetRepository extends CrudRepository<Asset, Long> {

}