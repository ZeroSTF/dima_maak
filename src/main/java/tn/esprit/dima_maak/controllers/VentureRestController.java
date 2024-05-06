package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.Venture;
import tn.esprit.dima_maak.serviceimpl.VentureServicesImpl;
import tn.esprit.dima_maak.services.IVentureServices;

import java.io.IOException;
import java.util.List;

@RequestMapping("/venture")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class VentureRestController {

    private IVentureServices ventureServices;

    @PostMapping("/add")
    public Venture addVenture (@RequestBody Venture venture){

        return ventureServices.addVenture(venture);
    }

    /*@PutMapping("/update")
    public Venture updateVenture (@RequestBody Venture venture){

        return ventureServices.updateVenture(venture);
    }*/
    @PutMapping("/update/{idV}")
    public ResponseEntity<Venture> updateVenture(@PathVariable Long idV, @RequestBody Venture venture) {

            Venture updateVenture = ventureServices.updateVenture(idV, venture);
            return  ResponseEntity.ok(updateVenture);

    }

    @DeleteMapping("/delete/{idV}")
    public ResponseEntity<String> deleteVenture(@PathVariable("idV") Long idV) {
        boolean deleted = ventureServices.deleteVenture(idV);
        if (deleted) {
            return ResponseEntity.ok("Venture deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venture with ID " + idV + " does not exist.");
        }
    }

    @GetMapping("/get/{idV}")
    public Venture getVentureById(@PathVariable("idV") Long idV) {
        return ventureServices.getVentureById(idV);
    }

    @GetMapping("/getAllVenture")
    public List<Venture> getVenture() {
        List<Venture> listVenture = ventureServices.getAllVenture();
        return listVenture;
    }

    @PostMapping("/{idV}/updateStatus")
    public ResponseEntity<String> updateVentureStatus(@PathVariable Long idV) {
        ventureServices.updateVentureStatus(idV);
        return ResponseEntity.ok("Venture status updated successfully");
    }

    @PostMapping("/processExcel")
    public ResponseEntity<String> processExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please upload a file.");
        }

        try {
            ventureServices.processExcelFile(file);
            return ResponseEntity.ok("Excel file processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Excel file: " + e.getMessage());
        }
    }



    @PutMapping("/updateAllStatus")
    public boolean updateAllVentureStatus() {
        return ventureServices.updateAllVenture();
    }


    @DeleteMapping("/deleteExpired")
    public boolean deleteExpiredVentures() {
        return ventureServices.deleteVenturesExpired();
    }
}