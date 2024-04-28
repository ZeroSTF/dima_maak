package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Leasing;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.services.ILeasingService;
import tn.esprit.dima_maak.services.IUserService;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/leasings")
@AllArgsConstructor
public class LeasingRestController {

    private final ILeasingService leasingService;
    private  final IUserService userService ;



    @PostMapping("/add")
    public Leasing createLeasing(@RequestBody Leasing leasing,@RequestParam("iduser") Long iduser,@RequestParam("iddemende") Long iddemende) {
        return leasingService.createLeasing(leasing,iduser,iddemende);
    }

    @GetMapping("/{leaseid}")
    public ResponseEntity<Leasing> getLeasingById(@PathVariable("leaseid") Long leaseId) {
        Optional<Leasing> leasing = leasingService.getLeasingById(leaseId);
        if (leasing.isPresent()) {
            return ResponseEntity.ok(leasing.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{leaseid}")
    public ResponseEntity<Void> deleteLeasingById(@PathVariable("leaseid") Long leaseId) {
        Optional<Leasing> existingLeasing = leasingService.getLeasingById(leaseId);
        if (existingLeasing.isPresent()) {
            leasingService.deleteLeasingById(leaseId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/assignusertoleasing/{id}/{leaseid}")
    public String assignUserToLeasing(@PathVariable Long id, @PathVariable Long leaseid) {
        return leasingService.assignUserToLeasing(id , leaseid );
    }


    @GetMapping("/{leasingId}/interest-rate")
    public float getAnnualInterestRate(@PathVariable Long leasingId) {
        Leasing leasing = leasingService.getLeasingById(leasingId)
                .orElseThrow(() -> new NoSuchElementException("Leasing not found with id: " + leasingId));
        return leasingService.calculateAnnualInterestRate(leasing);
    }

    @GetMapping("/{leasingId}/late-payment-percentage")
    public float getLatePaymentPercentage(@PathVariable Long leasingId) {
        Optional<Leasing> leasing = leasingService.getLeasingById(leasingId);
        if (leasing.isPresent()) {
            return leasingService.calculateLatePaymentPercentage(leasing.get());
        } else {
            // Gérer le cas où le leasing n'existe pas
            throw new OpenApiResourceNotFoundException("Leasing not found with id: " + leasingId);
        }
    }

}