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

   /* @DeleteMapping("/delete/{id}")
    public String deleteVenture(@PathVariable("id") Long id){
        ventureServices.deleteVenture(id);
        return "venture deleted !";}*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVenture(@PathVariable("id") Long id) {
        boolean deleted = ventureServices.deleteVenture(id);
        if (deleted) {
            return ResponseEntity.ok("Venture deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venture with ID " + id + " does not exist.");
        }
    }


    @GetMapping("/get/{id}")
    public Venture getVentureById(@PathVariable("id") Long id) {
        return ventureServices.getVentureById(id);
    }


    @GetMapping("/getAllVenture")
    public List<Venture> getVenture() {
        List<Venture> listVenture = ventureServices.getAllVenture();
        return listVenture;
    }


}
