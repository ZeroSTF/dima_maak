package tn.esprit.dima_maak.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Return;
import tn.esprit.dima_maak.services.IReturnServices;

import java.util.List;

@RequestMapping("/return")
@RestController
@AllArgsConstructor
public class ReturnRestController {
    private IReturnServices iReturnServices;

    @PostMapping("/add")
    public Return addReturn(@RequestBody Return treturn) {

        return iReturnServices.addReturn(treturn);
    }

    @PutMapping("/update")
    public Return updateReturn (@RequestBody Return treturn){

        return iReturnServices.updateReturn(treturn);
    }


    @DeleteMapping("/delete/{idR}")
    public ResponseEntity<String> deleteReturn(@PathVariable("idR") Long idR) {
        boolean deleted = iReturnServices.deleteReturn(idR);
        if (deleted) {
            return ResponseEntity.ok("Return deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Return with ID " + idR + " does not exist.");
        }
    }

    @GetMapping("/get/{idR}")
    public Return getReturnById(@PathVariable("idR") Long idR) {
        return iReturnServices.getReturnById(idR);
    }

    @GetMapping("/getAllReturn")
    public List<Return> getVenture() {
        return ResponseEntity.ok().body(iReturnServices.getAllReturn()).getBody();
    }


}
