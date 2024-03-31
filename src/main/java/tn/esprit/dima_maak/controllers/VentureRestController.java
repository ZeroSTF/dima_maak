package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Venture;
import tn.esprit.dima_maak.services.IVentureServices;

import java.util.List;

@RequestMapping("/venture")
@RestController
@AllArgsConstructor
public class VentureRestController {

    private IVentureServices ventureServices;

    @PostMapping("/add")
    public Venture addVenture (@RequestBody Venture venture){

        return ventureServices.addVenture(venture);
    }

    @PutMapping("/update")
    public Venture updateVenture (@RequestBody Venture venture){

        return ventureServices.updateVenture(venture);
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
    }


