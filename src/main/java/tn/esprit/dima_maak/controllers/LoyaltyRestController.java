package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Loyalty;
import tn.esprit.dima_maak.services.ILoyaltyService;

import java.util.List;

@Tag(name = "Loyalty management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/loyalty")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoyaltyRestController {
    private final ILoyaltyService loyaltyService;

    @Operation(description = "get all loyalties")
    @GetMapping("/getAll")
    public List<Loyalty> getLoyalties() {
        return loyaltyService.retrieveAllLoyalties();
    }

    @Operation(description = "get one loyalty")
    @GetMapping("/get/{loyalty-id}")
    public Loyalty retrieveLoyalty(@PathVariable("loyalty-id") Long loyaltyId) {
        return loyaltyService.retrieveLoyalty(loyaltyId);
    }

    @Operation(description = "add a loyalty")
    @PostMapping("/add")
    public Loyalty addLoyalty(@RequestBody Loyalty c) {
        return loyaltyService.addLoyalty(c);
    }

    @Operation(description = "delete a loyalty")
    @DeleteMapping("/delete/{loyalty-id}")
    public void removeLoyalty(@PathVariable("loyalty-id") Long loyaltyId) {
        loyaltyService.removeLoyalty(loyaltyId);
    }

    @Operation(description = "edit a loyalty")
    @PutMapping("/update")
    public Loyalty modifyLoyalty(@RequestBody Loyalty c) {
        return loyaltyService.modifyLoyalty(c);
    }
}
