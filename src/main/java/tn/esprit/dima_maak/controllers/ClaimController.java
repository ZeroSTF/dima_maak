package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.ClaimRepository;
import tn.esprit.dima_maak.repositories.InsuranceRepository;
import tn.esprit.dima_maak.serviceimpl.InsurancePServiceImpl;
import tn.esprit.dima_maak.serviceimpl.UserServiceImpl;
import tn.esprit.dima_maak.serviceimpl.WeatherService;
import tn.esprit.dima_maak.services.IClaimService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/Claim")

public class ClaimController {
    @Autowired
    private IClaimService claimservice;
    @Autowired
    InsuranceRepository insuranceRepository;
    @Autowired
    ClaimRepository claimRepository;


    @PostMapping("/save")
    public Claim addClaim(@RequestBody Claim c ){
        return claimservice.addClaim(c);

    }
    @PostMapping(value = "/savewithimage", consumes = {"multipart/form-data"})
    public String createAd(
            @RequestParam("adsImages") MultipartFile[] adsImages,
            @RequestParam("idInsurance") Long idInsurance,
            @ModelAttribute Claim c
    ) throws IOException {
        String uploadDirectory = "src/main/resources/static/images/ads";
        String adsImagesString = "";

        for (MultipartFile imageFile : adsImages) {
            adsImagesString += claimservice.saveImageToStorage(uploadDirectory, imageFile);
        }
        c.setImage(adsImagesString);
       Insurance insurance= insuranceRepository.findById(idInsurance).get();
        c.setInsurance(insurance);
        claimservice.addClaim(c);
        return adsImagesString;
    }

    @GetMapping("/findbyid/{idclaim}")
    public Claim findclaimbyid(@PathVariable Long idclaim){
        return claimservice.getClaimbyid(idclaim);
    }

    @DeleteMapping("deleteclaim/{idc}")
    public String deleteClaim(@PathVariable Long idc){
        claimservice.deleteClaim(idc);
        return "claim deleted !";
    }
    @GetMapping("/all")
    public List<Claim> getallclaims(){
        return claimservice.getALL();
    }
    @PutMapping("/updateclaim")
    public Claim updateclaim(@RequestBody Claim c)
    {
        return claimservice.updateclaim(c);
    }
   /* @GetMapping("/analyze/{userId}")
    public String analyzeRisk(@PathVariable Long userId) {
        return claimservice.analyzeRisk(userId);
    }*/
    @PutMapping ("/addclaimandassigntoinsurance/{idin}")
    public Claim addclaimandassigntoinsurance (@RequestBody Claim c, @PathVariable Long idin)
    {
        return claimservice.addclaimandassigntoinsurance(c,idin);
    }
    @GetMapping("/gettotalclaim")
    public Long gettotalclaims ()
    {
        return claimservice.getTotalClaimCount();
    }
    @GetMapping("/countclaimsbystatus/{cstatus}")
    public Long countclaimsbystatus(@PathVariable CStatus cstatus)
    {
        return claimservice.countclaimsbystatus(cstatus);
    }
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private  WeatherService weatherService;



    @GetMapping("/autoaddclaim")

    public String autoadd() {

        Iterable<Claim> claims=claimRepository.findAll();

        for (Claim claim : claims
        ){
            Weather weather = weatherService.getWeather(claim.getInsurance().getUser().getAddress().getCity());
            Map<String, Object> main = weather.getMain();
            //double temp = (double) main.get("temp") ;

            //double temp= 1f;
           double temp= 45f;
            //System.out.println("temp = "+ temp);
            if (claim.getInsurance().getInsuranceP().getType().equals(IType.Agriculteur_Insurance) && temp >=40){
                Claim claim1=new Claim();
                claim1.setStatus(CStatus.Auto);
                claim1.setAmount(10.5f);
                claim1.setImage(claim.getImage());
                claim1.setInsurance(claim.getInsurance());
                claim1.setDate(LocalDate.now());
                claim1.setDetails("temp > 40 ");
                claimservice.addClaim(claim1);
                break;
            } else if (claim.getInsurance().getInsuranceP().getType().equals(IType.Agriculteur_Insurance) && temp <4) {
                Claim claim1=new Claim();
                claim1.setStatus(CStatus.Auto);
                claim1.setAmount(10.5f);
                claim1.setImage(claim.getImage());
                claim1.setInsurance(claim.getInsurance());
                claim1.setDate(LocalDate.now());
                claim1.setDetails("temp < 4");
                claimservice.addClaim(claim1);
                break;
            }
        }


        return "auto weather claim added to all agriculteurs";
    }




}
