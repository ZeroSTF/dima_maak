package tn.esprit.dima_maak.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.InsuranceP;
import tn.esprit.dima_maak.services.IInsurancePService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/InsuranceP")

public class InsurancePController {
    @Autowired
    private IInsurancePService ipservices;

    @PostMapping("/save")
    public InsuranceP addInsuranceP(@RequestBody InsuranceP ip ){
        return ipservices.addInsuranceP(ip);

    }
    @GetMapping("/findbyid/{idinsurancep}")
    public InsuranceP findinsurancePbyid(@PathVariable Long idinsurancep){
        return ipservices.getInsurancePById(idinsurancep);
    }

    @DeleteMapping("deleteinsurancep/{idinsurancep}")
    public String deleteInsuranceP(@PathVariable Long idinsurancep){
        ipservices.deleteInsuranceP(idinsurancep);
        return "insurance pack deleted !";
    }
    @GetMapping("/all")
    public List<InsuranceP> getallinsuranceP(){
        return ipservices.getALL();
    }
    @PutMapping("/updateinsurancep")
    public InsuranceP updateinsurancep(@RequestBody InsuranceP insurancep)
    {
        return ipservices.updateInsuranceP(insurancep);
    }
    @GetMapping("/gettotalinsurancepackscount")
    public  Long gettotalinsurancepackscount ()
    {
        return ipservices.gettotalinsurancepackscount();
    }
    @GetMapping("/countInsurancePacksByType/{type}")
    public Long countInsurancePacksByType(@PathVariable IType type)
    {
        return ipservices.countInsurancePacksByType(type);
    }
    @GetMapping("/calculatePercentageByType/{type}")
    public String calculatePercentageByType(@PathVariable IType type)
    {
        return ipservices.calculatePercentageByType(type);
    }

}
