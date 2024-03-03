package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.services.IInsuranceService;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Insurance")

public class InsuranceController {
    private IInsuranceService insuranceservice;
    @PostMapping("/save")
    public Insurance addInsurance(@RequestBody Insurance insurance ){
        return insuranceservice.addInsurance(insurance);

    }
    @GetMapping("/findbyid/{idinsurance}")
    public Insurance findinsurancebyid(@PathVariable Long idinsurance){
        return insuranceservice.getInsuranceById(idinsurance);
    }

    @DeleteMapping("deleteinsurance/{idinsurance}")
    public String deleteInsurance(@PathVariable Long idinsurance){
        insuranceservice.deleteInsurance(idinsurance);
        return "insurance deleted !";
    }
    @GetMapping("/all")
    public List<Insurance> getallinsurance(){
        return insuranceservice.getALL();
    }
    @PutMapping("/updateinsurance")
    public Insurance updateinsurance(@RequestBody Insurance insurance)
    {
        return insuranceservice.updateInsurance(insurance);
    }



    }







