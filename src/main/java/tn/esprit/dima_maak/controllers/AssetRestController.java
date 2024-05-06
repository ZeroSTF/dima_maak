package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.entities.AssetType;
import tn.esprit.dima_maak.entities.Demande;
import tn.esprit.dima_maak.entities.Leasing;
import tn.esprit.dima_maak.repositories.IAssetRepository;
import tn.esprit.dima_maak.serviceimpl.AssetServiceImpl;
import tn.esprit.dima_maak.serviceimpl.Demendeservice;
import tn.esprit.dima_maak.serviceimpl.LeasingServiceImpl;
import tn.esprit.dima_maak.services.ILeasingService;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AssetRestController {
    public final Demendeservice demendeservice;
    public final AssetServiceImpl assetService;
    public final ILeasingService leasingService;


    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public Asset createAsset(@RequestPart MultipartFile[] adsImages, @ModelAttribute Asset asset, @RequestParam("id") Long id) throws IOException {
        return assetService.createAsset(adsImages,asset,id);
    }

    @GetMapping("/{assetid}")
    public ResponseEntity<Asset> getAssetById(@PathVariable("assetid") Long id) {
        Optional<Asset> assetOptional = assetService.getAssetById(id);
        return assetOptional.map(asset -> new ResponseEntity<>(asset, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    private static final String IMAGES_FOLDER = "src/main/resources/static/images/ads";

    @GetMapping("/images/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get(IMAGES_FOLDER, imageName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Asset> updateAsset(@RequestParam("idasset") Long id, @RequestBody Asset updatedAsset) {

        Asset asset = assetService.updateAsset(updatedAsset,id);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{assetid}")
    public ResponseEntity<Void> deleteAsset(@PathVariable("assetid") Long id) {
        assetService.deleteAssetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAll();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/leasing/{id}/duration")
    public int calculateLeaseDuration(@PathVariable Long id) {
        Optional<Leasing> leasingOptional = leasingService.getLeasingById(id);
        if (leasingOptional.isPresent()) {
            Leasing leasing = leasingOptional.get();
            return assetService.calculateLeaseDuration(leasing);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Leasing with ID " + id + " not found");
        }
    }

    @GetMapping("/{id}/residualvalue")
    public float calculateResidualValue(@PathVariable Long id) {

        Optional<Leasing> leasingOptional = leasingService.getLeasingById(id);

        if (leasingOptional.isPresent()) {
            Leasing leasing = leasingOptional.get();

            return assetService.calculateResidualValue(leasing);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Leasing with ID " + id + " not found");
        }
    }

    @GetMapping("/compare/{beforeAssetId}/{afterAssetId}")
    public String compareEquipmentCondition(@PathVariable Long beforeAssetId, @PathVariable Long afterAssetId) {
        Optional<Asset> beforeAssetOptional = assetService.getAssetById(beforeAssetId);
        Optional<Asset> afterAssetOptional = assetService.getAssetById(afterAssetId);

        if (!beforeAssetOptional.isPresent() || !afterAssetOptional.isPresent()) {
            return "Assets not found";
        }

        Asset beforeAsset = beforeAssetOptional.get();
        Asset afterAsset = afterAssetOptional.get();
        String comparisonResult = assetService.compareEquipmentCondition(beforeAsset, afterAsset);

        return comparisonResult;
    }
    @GetMapping("/type_distribution")
    public ResponseEntity<Map<AssetType, Long>> getAssetTypeDistribution() {
        Map<AssetType, Long> distribution = assetService.getAssetTypeDistribution();
        return ResponseEntity.ok(distribution);
    }
    @GetMapping("/{assetId}/monthlyPay")
    public ResponseEntity<Float> calculateMonthlyPayment(@PathVariable Long assetId) {
        Demande demande = demendeservice.getdemandeById(assetId);
        Asset asset = demande.getAsset();


        // Vérifiez si l'actif et le contrat de location associé existent || asset.getLeasing() == null
        if (asset == null ) {
            throw new IllegalArgumentException("L'actif ou le contrat de location associé est null.");
        }

        // Calculez le paiement mensuel
        float monthlyPayment = assetService.calculateMonthlyPayment(demande);

        // Retournez le paiement mensuel dans la réponse
        return ResponseEntity.ok(monthlyPayment);
    }

}








