package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Claim;
import tn.esprit.dima_maak.entities.Insurance;
import tn.esprit.dima_maak.services.IClaimService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Claim")

public class ClaimController {
    private IClaimService claimservice;

    @PostMapping("/save")
    public Claim addClaim(@RequestBody Claim c ){
        return claimservice.addClaim(c);

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
}
