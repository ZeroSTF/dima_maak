package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Asset;
import tn.esprit.dima_maak.serviceimpl.AssetServiceImpl;
import tn.esprit.dima_maak.services.IAssetService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
public class AssetRestController {

    private final AssetServiceImpl assetService;

    @Autowired
    public AssetRestController(AssetServiceImpl assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        Asset createdAsset = assetService.createAsset(asset);
        return new ResponseEntity<>(createdAsset, HttpStatus.CREATED);
    }

    @GetMapping("/{assetid}")
    public ResponseEntity<Asset> getAssetById(@PathVariable("assetid") Long id) {
        Optional<Asset> assetOptional = assetService.getAssetById(id);
        return assetOptional.map(asset -> new ResponseEntity<>(asset, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{assetid}")
    public ResponseEntity<Asset> updateAsset(@PathVariable("assetid") Long id, @RequestBody Asset updatedAsset) {
        updatedAsset.setAsstid(id);
        Asset asset = assetService.updateAsset(updatedAsset);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @DeleteMapping("/{assetid}")
    public ResponseEntity<Void> deleteAsset(@PathVariable("assetid") Long id) {
        assetService.deleteAssetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAll();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}