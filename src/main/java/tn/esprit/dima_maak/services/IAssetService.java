package tn.esprit.dima_maak.services;

import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.AssetType;
import tn.esprit.dima_maak.entities.Leasing;

import java.util.List;
import java.util.Map;
import java.util.Optional;



    public interface IAssetService {
        public Asset createAsset(Asset asset,Long iduser)  ;
        Optional<Asset> getAssetById(Long id);
        public Asset updateAsset(Asset updatedAsset,Long idasset);
        void deleteAssetById(Long id);
        List<Asset> getAll();
        float calculateResidualValue(Leasing leasing) ;

      //  public float calculateMonthlyPayment(Asset asset) ;
      float getInitialAmount(Asset asset) ;
     //  float calculateAnnualInterestRate(Leasing leasing) ;



         String compareEquipmentCondition(Asset beforeRental, Asset afterRental) ;
        Map<AssetType, Long> getAssetTypeDistribution() ;
    }
