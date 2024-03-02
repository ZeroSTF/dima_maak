package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.repositories.IAssetRepository;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AssetServiceImpl {
    private IAssetRepository assetRepository;

    public Asset createAsset(Asset a) {
        return assetRepository.save(a);}

    public Optional<Asset> getAssetById(Long idasset) {
        return assetRepository.findById(idasset);
    }

    public Asset updateAsset(Asset updatedAsset) {
        return assetRepository.save(updatedAsset);
    }
    public void deleteAssetById(Long id) {
        assetRepository.deleteById(id);}



    public List<Asset> getAll() {
        return(List<Asset>) assetRepository.findAll();}


     }
