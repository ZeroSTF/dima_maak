package tn.esprit.dima_maak.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.IType;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IInsuranceService;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/Insurance")
@CrossOrigin(origins = "*")

public class InsuranceController {
    @Autowired
    private IInsuranceService insuranceservice;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/save")
    public Insurance addInsurance(@RequestBody Insurance insurance ){
        return insuranceservice.addInsurance(insurance);

    }
    @PostMapping("/savepackanduser/{idpack}/{iduser}")
    public Insurance savepackanduser(@PathVariable Long idpack,@PathVariable Long iduser ){
        return insuranceservice.createInsurance(idpack,iduser);

    }
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        List<User> users = userRepository.findUsersByEmail(email);
        if (!users.isEmpty()) {
            for (User user : users) {
                if (password.equals(user.getPassword())) {
                    return ResponseEntity.ok(user);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping ("/updatedinc/{Idinsurance}")
    public Insurance updatedinc(@PathVariable Long Idinsurance ){
        return insuranceservice.updatedInsurance(Idinsurance);

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
    @GetMapping("/allbyuser")
    public List<Insurance> getbyuser(@RequestParam("iduser") Long iduser){
        return insuranceservice.getallbyuser(iduser);
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







