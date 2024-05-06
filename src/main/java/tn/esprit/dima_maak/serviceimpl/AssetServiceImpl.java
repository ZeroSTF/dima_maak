package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.IAssetRepository;
import tn.esprit.dima_maak.repositories.ILeasingRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IAssetService;
import tn.esprit.dima_maak.services.ILeasingService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@AllArgsConstructor
public class AssetServiceImpl implements IAssetService {


    private IAssetRepository assetRepository;
    private final UserRepository userRepository;
    private final ILeasingRepository leasingRepository;

    public Asset createAsset(MultipartFile[] adsImages,Asset asset,Long iduser) throws IOException {

        User user=userRepository.findById(iduser).orElse(null);
        String uploadDirectory = "src/main/resources/static/images/ads";
        String adsImagesString = "";
        for (MultipartFile imageFile : adsImages) {
            adsImagesString += saveImageToStorage(uploadDirectory, imageFile);
        }
        asset.setImage(adsImagesString);
        asset.setUser(user);
        return assetRepository.save(asset);
    }
    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(filePath);
        return uniqueFileName;
    }
    public Optional<Asset> getAssetById(Long idasset) {
        return assetRepository.findById(idasset);
    }

    public Asset updateAsset(Asset updatedAsset,Long idasset) {

        Asset asset= assetRepository.findById(idasset).orElse(null);
        asset.setType(updatedAsset.getType());
        asset.setDescription(updatedAsset.getDescription());
        asset.setInitialAmount(updatedAsset.getInitialAmount());
        asset.setPurchasedate(updatedAsset.getPurchasedate());
        asset.setWarrantyExpirationDate(updatedAsset.getWarrantyExpirationDate());
        asset.setMaintenanceStatus(updatedAsset.getMaintenanceStatus());
        asset.setLastMaintenanceDate(updatedAsset.getLastMaintenanceDate());
    asset.setFunctions(updatedAsset.getFunctions());
    asset.setPower(updatedAsset.getPower());
    asset.setProductionCapacity(updatedAsset.getProductionCapacity());
    asset.setOperationalEfficiency(updatedAsset.getOperationalEfficiency());
    asset.setServiceLevel(updatedAsset.getServiceLevel());
    asset.setFuelConsumption(updatedAsset.getFuelConsumption());
    asset.setEngineCondition(updatedAsset.getEngineCondition());
    asset.setMileage(updatedAsset.getMileage());
    asset.setAnnualInterestRate(updatedAsset.getAnnualInterestRate());

        return assetRepository.save(asset);
    }

    public void deleteAssetById(Long id) {
        assetRepository.deleteById(id);
    }


    public List<Asset> getAll() {
        return (List<Asset>) assetRepository.findAll();
    }
//leasing
    public int calculateLeaseDuration(Leasing leasing) {
        LocalDate startDate = leasing.getStartdate();
        LocalDate endDate = leasing.getEnddate();


        // Calcul de la durée du leasing en mois
        long months = ChronoUnit.MONTHS.between(startDate, endDate);


        return (int) months;
    }

//leasging
    @Override
    public float calculateResidualValue( Leasing leasing) {

        int leaseDuration = calculateLeaseDuration(leasing);



        float annualDepreciation = leasing.getInitialValue() / leaseDuration;


        // Calcul de la dépréciation totale sur la durée du leasing
        float totalDepreciation = annualDepreciation * leaseDuration;


        // Calcul de la valeur résiduelle
        float residualValue = leasing.getInitialValue() - totalDepreciation;


        return residualValue;

    }
//|| asset.getLeasing() == null
    /// monthly payment
public float calculateMonthlyPayment(Demande demande) {
    if (demande == null) {
        throw new IllegalArgumentException("La demande est nulle.");
    }

    float monthlyPayment = 0.0f;
    for (Leasing leasing : demande.getLeasingList()) {
        if (demande.getId() == leasing.getDemande().getId()) {
            float totalAmount = demande.getAsset().getPrice() != null ? demande.getAsset().getPrice() : 0.0f;
            float montantparmonth = totalAmount/12;
            float monthlyPaymentPercentage = 0.25f;

            monthlyPayment = montantparmonth * monthlyPaymentPercentage;
            break;
        }
    }
    return Math.round(monthlyPayment * 100) / 100.0f;
}




    public float getInitialAmount(Asset asset) {
        return asset.getInitialAmount();
    }





    /*public float calculateAdditionalFees(Leasing leasing) {
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
    }*/





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





        return comparisonResult.toString();
    }



    private String compareVehicleEquipment(Asset before, Asset after) {
        StringBuilder comparisonResult = new StringBuilder();


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

        Map<AssetType, Long> assetTypeDistribution = new HashMap<>();

        // Parcourez la liste des actifs loués et comptez le nombre d'actifs pour chaque type
        for (Asset asset : leasedAssets) {
            AssetType type = asset.getType();
            assetTypeDistribution.put(type, assetTypeDistribution.getOrDefault(type, 0L) + 1);
        }

        return assetTypeDistribution;
    }

}
















