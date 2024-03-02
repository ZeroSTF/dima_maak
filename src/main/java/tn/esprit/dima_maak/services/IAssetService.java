package tn.esprit.dima_maak.services;

import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Asset;

import java.util.List;
import java.util.Optional;



    public interface IAssetService {
        Asset createAsset(Asset a);
        Optional<Asset> getAssetById(Long id);
        Asset updateAsset(Asset updatedAsset);
        void deleteAssetById(Long id);
        List<Asset> getAll();
}
