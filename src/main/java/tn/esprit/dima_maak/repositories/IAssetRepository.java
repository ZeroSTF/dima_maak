package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.Leasing;

import java.util.Optional;

public interface IAssetRepository extends CrudRepository<Asset, Long> {
    public Optional<Asset> findById(Long  leaseid);

}