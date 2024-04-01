package tn.esprit.dima_maak.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.services.IInsuranceService;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @PutMapping("/addinsuranceandassigntouser/{iduser}")
    public Insurance addinsuranceandassigntouser (@RequestBody Insurance insurance,@PathVariable Long iduser)
    {
        return insuranceservice.addinsuranceandassigntouser(insurance,iduser);
    }
    @PutMapping("/addinsuranceandassigntoinsurancepack/{idinspack}")
    public Insurance addinsuranceandassigntoinsurancepack(@RequestBody Insurance insurance,@PathVariable Long idinspack)
    {
        return insuranceservice.assinsuranceandassigntoinsurancepack(insurance,idinspack);
    }
    @GetMapping("/retrieveinsurancebypacktype/{typepack}")
    public List<Insurance> retrieveinsurancebypacktype(@PathVariable IType typepack)
    {
        return insuranceservice.retrieveinsurancebypacktype(typepack);
    }
    @GetMapping("/gettotalinsurance")
    public Long gettotalinsurance()
    {
        return insuranceservice.gettotalinsurance();
    }
   /* @GetMapping("/getInsuranceTypeDistribution")
    public Map<String, Long> getInsuranceTypeDistribution()
    {
        return insuranceservice.getInsuranceTypeDistribution();
    }*/
    @PutMapping("/assigninsurancetoinsurancepack/{idinsurance}/{idinsurancepack}")
    public Insurance assigninsurancetoinsurancepack (@PathVariable Long idinsurance,@PathVariable Long idinsurancepack)
    {
        return insuranceservice.assigninsurancetoinsurancepack(idinsurance,idinsurancepack);
    }
    @GetMapping("/countInsurancesByPackType/{packtype}")
    public Long countInsurancesByPackType(@PathVariable IType packtype)
    {
        return insuranceservice.countInsurancesByPackType(packtype);
    }
@GetMapping("/calculatePercentageByType/{packtype}")
    public String calculatePercentageByType (@PathVariable IType packtype)
{
    return insuranceservice.calculatePercentageByType(packtype);
}
@PutMapping("/assigninsurancetouser/{idinsurance}/{iduser}")
public Insurance assigninsurancetouser(@PathVariable Long idinsurance,@PathVariable Long iduser)
{
    return insuranceservice.assigninsurancetouser(idinsurance,iduser);
}
@GetMapping("/findTotalCoverageAmountByPackType/{packtype}")
    public  float findTotalCoverageAmountByPackType(@PathVariable IType packtype)
{
    return insuranceservice.findTotalCoverageAmountByPackType(packtype);
}


    }







