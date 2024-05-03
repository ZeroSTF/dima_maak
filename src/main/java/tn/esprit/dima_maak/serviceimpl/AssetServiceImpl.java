package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.AssetType;
import tn.esprit.dima_maak.entities.Leasing;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.IAssetRepository;
import tn.esprit.dima_maak.repositories.ILeasingRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IAssetService;
import tn.esprit.dima_maak.services.ILeasingService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AssetServiceImpl implements IAssetService {


    private IAssetRepository assetRepository;
    private final UserRepository userRepository;


    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public Optional<Asset> getAssetById(Long idasset) {
        return assetRepository.findById(idasset);
    }

    public Asset updateAsset(Asset updatedAsset) {
        return assetRepository.save(updatedAsset);
    }

    public void deleteAssetById(Long id) {
        assetRepository.deleteById(id);
    }


    public List<Asset> getAll() {
        return (List<Asset>) assetRepository.findAll();
    }

    public int calculateLeaseDuration(Leasing leasing) {
        LocalDate startDate = leasing.getStartdate();
        LocalDate endDate = leasing.getEnddate();


        // Calcul de la durée du leasing en mois
        long months = ChronoUnit.MONTHS.between(startDate, endDate);


        return (int) months;
    }


    @Override
    public float calculateResidualValue(float initialValue, Leasing leasing) {
        // Calcul de la durée du leasing
        int leaseDuration = calculateLeaseDuration(leasing);


        // Supposons une dépréciation linéaire sur la durée du leasing
        float annualDepreciation = initialValue / leaseDuration;


        // Calcul de la dépréciation totale sur la durée du leasing
        float totalDepreciation = annualDepreciation * leaseDuration;


        // Calcul de la valeur résiduelle
        float residualValue = initialValue - totalDepreciation;


        return residualValue;

    }

    public float calculateMonthlyPayment(Asset asset) {
        if (asset == null || asset.getLeasing() == null) {
            throw new IllegalArgumentException("L'actif ou le contrat de location associé est null.");
        }

        Leasing leasing = asset.getLeasing();
        float totalAmount = asset.getInitialAmount() != null ? asset.getInitialAmount() : 0.0f;
        float additionalFees = leasing.getAdditionalfee() != null ? leasing.getAdditionalfee() : 0.0f;
        int durationMonths = calculateLeaseDuration(leasing);


       float monthlyInterestRate = asset.getAnnualInterestRate() / 12 / 100;

        float monthlyPayment = (totalAmount + additionalFees) * monthlyInterestRate /
                (1 - (float) Math.pow(1 + monthlyInterestRate, -durationMonths));

        return Math.round(monthlyPayment * 100) / 100.0f;
    }

    public float getInitialAmount(Asset asset) {
        return asset.getInitialAmount();
    }





    public float calculateAdditionalFees(Leasing leasing) {
        AssetType assetType = leasing.getAsset().getType();
        int leaseDurationMonths = calculateLeaseDuration(leasing);

        // les frais supplémentaires dépendent du type d'actif et de la durée du leasing
        float additionalFees = 0.0f;

        switch (assetType) {
            case agricultural:

                additionalFees = 100.0f * leaseDurationMonths;
                break;
            case commercial:

                additionalFees = 200.0f * leaseDurationMonths;
                break;
            case medical:

                additionalFees = 300.0f * leaseDurationMonths;
                break;
            case vehicles:

                additionalFees = 150.0f * leaseDurationMonths;
                break;
        }

        return additionalFees;
    }




    public String compareEquipmentCondition(Asset beforeRental, Asset afterRental) {
        StringBuilder comparisonResult = new StringBuilder();


        if (beforeRental.getType() == AssetType.agricultural && afterRental.getType() == AssetType.agricultural) {
            comparisonResult.append(compareAgriculturalEquipment(beforeRental, afterRental));
        } else if (beforeRental.getType() == AssetType.commercial && afterRental.getType() == AssetType.commercial) {
            comparisonResult.append(compareCommercialEquipment(beforeRental, afterRental));
        } else if (beforeRental.getType() == AssetType.medical && afterRental.getType() == AssetType.medical) {
            comparisonResult.append(compareMedicalEquipment(beforeRental, afterRental));
        } else if (beforeRental.getType() == AssetType.vehicles && afterRental.getType() == AssetType.vehicles) {
            comparisonResult.append(compareVehicleEquipment(beforeRental, afterRental));
        } else {
            comparisonResult.append("Types d'équipement non compatibles pour la comparaison");
        }

        return comparisonResult.toString();
    }


    private String compareAgriculturalEquipment(Asset before, Asset after) {
        StringBuilder comparisonResult = new StringBuilder();

        if (before.getPower() != after.getPower()) {
            comparisonResult.append("Changement de la puissance: ")
                    .append(before.getPower())
                    .append(" -> ")
                    .append(after.getPower())
                    .append("\n");
        }


        if (before.getProductionCapacity() != after.getProductionCapacity()) {
            comparisonResult.append("Changement de la capacité de production: ")
                    .append(before.getProductionCapacity())
                    .append(" -> ")
                    .append(after.getProductionCapacity())
                    .append("\n");
        }
        return comparisonResult.toString();
    }


    private String compareCommercialEquipment(Asset before, Asset after) {
        StringBuilder comparisonResult = new StringBuilder();

        if (before.getProductionCapacity() != after.getProductionCapacity()) {
            comparisonResult.append("Changement de la capacité de production: ")
                    .append(before.getProductionCapacity())
                    .append(" -> ")
                    .append(after.getProductionCapacity())
                    .append("\n");
        }

        // Comparaison de l'efficacité opérationnelle
        if (before.getOperationalEfficiency() != after.getOperationalEfficiency()) {
            comparisonResult.append("Changement de l'efficacité opérationnelle: ")
                    .append(before.getOperationalEfficiency())
                    .append(" -> ")
                    .append(after.getOperationalEfficiency())
                    .append("\n");
        }

        // Comparaison du niveau de service
        if (!before.getServiceLevel().equals(after.getServiceLevel())) {
            comparisonResult.append("Changement du niveau de service: ")
                    .append(before.getServiceLevel())
                    .append(" -> ")
                    .append(after.getServiceLevel())
                    .append("\n");
        }

        return comparisonResult.toString();
    }


    private String compareMedicalEquipment(Asset before, Asset after) {
        StringBuilder comparisonResult = new StringBuilder();


        if (!before.getFunctions().equals(after.getFunctions())) {
            comparisonResult.append("Changement des fonctions médicales disponibles: ")
                    .append(before.getFunctions())
                    .append(" -> ")
                    .append(after.getFunctions())
                    .append("\n");
        }


        if (!before.getMaintenanceStatus().equals(after.getMaintenanceStatus())) {
            comparisonResult.append("Changement de l'état de maintenance: ")
                    .append(before.getMaintenanceStatus())
                    .append(" -> ")
                    .append(after.getMaintenanceStatus())
                    .append("\n");
        }


        if (!before.getWarrantyExpirationDate().equals(after.getWarrantyExpirationDate())) {
            comparisonResult.append("Changement de la durée de garantie restante: ")
                    .append(before.getWarrantyExpirationDate())
                    .append(" -> ")
                    .append(after.getWarrantyExpirationDate())
                    .append("\n");
        }


        if (!before.getLastMaintenanceDate().equals(after.getLastMaintenanceDate())) {
            comparisonResult.append("Changement de la dernière date de maintenance: ")
                    .append(before.getLastMaintenanceDate())
                    .append(" -> ")
                    .append(after.getLastMaintenanceDate())
                    .append("\n");
        }

        return comparisonResult.toString();
    }



    private String compareVehicleEquipment(Asset before, Asset after) {
        StringBuilder comparisonResult = new StringBuilder();

        // Comparaison spécifique pour les véhicules
        // Comparaison de la consommation de carburant
        if (before.getFuelConsumption() != after.getFuelConsumption()) {
            comparisonResult.append("Changement de la consommation de carburant: ")
                    .append(before.getFuelConsumption())
                    .append(" -> ")
                    .append(after.getFuelConsumption())
                    .append("\n");
        }

        // Comparaison de l'état du moteur
        if (!before.getEngineCondition().equals(after.getEngineCondition())) {
            comparisonResult.append("Changement de l'état du moteur: ")
                    .append(before.getEngineCondition())
                    .append(" -> ")
                    .append(after.getEngineCondition())
                    .append("\n");
        }

        // Comparaison du kilométrage
        if (before.getMileage() != after.getMileage()) {
            comparisonResult.append("Changement du kilométrage: ")
                    .append(before.getMileage())
                    .append(" -> ")
                    .append(after.getMileage())
                    .append("\n");
        }



        return comparisonResult.toString();
    }


    public Map<AssetType, Long> getAssetTypeDistribution() {
        List<Asset> leasedAssets = getAll(); // Obtenez tous les actifs loués
       // logger.info("Nombre d'actifs récupérés : {}", leasedAssets.size());
        // Initialisez un HashMap pour stocker la répartition des types d'actifs
        Map<AssetType, Long> assetTypeDistribution = new HashMap<>();

        // Parcourez la liste des actifs loués et comptez le nombre d'actifs pour chaque type
        for (Asset asset : leasedAssets) {
            AssetType type = asset.getType();
            assetTypeDistribution.put(type, assetTypeDistribution.getOrDefault(type, 0L) + 1);
        }

        return assetTypeDistribution;
    }

}
















