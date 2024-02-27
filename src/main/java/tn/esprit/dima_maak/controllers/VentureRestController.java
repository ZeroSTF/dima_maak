package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Venture;
import tn.esprit.dima_maak.services.IVentureServices;

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

    @DeleteMapping("/delete/{id}")
    public String deleteVenture(@PathVariable("id") Long id){
        ventureServices.deleteVenture(id);
        return "venture deleted !";}


    @GetMapping("/get/{numPiste}")
    public Venture getVentureById(@PathVariable("id") Long id) {
        return ventureServices.getVentureById(id);
    }

}
