package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.Premium;
import tn.esprit.dima_maak.services.IPremiumService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Premium")

public class PremiumController {
    private IPremiumService premiumservice;

    @PostMapping("/save")
    public Premium addPremium(@RequestBody Premium premium ){
        return premiumservice.addPremium(premium);

    }
    @GetMapping("/findbyid/{idpremium}")
    public Premium findpremiumbyid(@PathVariable Long idpremium){
        return premiumservice.getPremiumById(idpremium);
    }

    @DeleteMapping("deletepremium/{idp}")
    public String deletePremium(@PathVariable Long idp){
        premiumservice.deletePremium(idp);
        return "premium deleted !";
    }
    @GetMapping("/all")
    public List<Premium> getallpremiums(){
        return premiumservice.getALL();
    }
    @PutMapping("/updatepremium")
    public Premium updateprmium(@RequestBody Premium p)
    {
        return premiumservice.updatePremium(p);
    }
}
