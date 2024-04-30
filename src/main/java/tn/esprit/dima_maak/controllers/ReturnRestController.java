package tn.esprit.dima_maak.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Return;
import tn.esprit.dima_maak.services.IReturnServices;


import java.util.List;

@RequestMapping("/return")
@RestController
@RequiredArgsConstructor
public class ReturnRestController {

    private final IReturnServices iReturnServices;
    @PostMapping("/add")
    public Return addReturn(@RequestBody Return treturn) {

        return iReturnServices.addReturn(treturn);
    }
    @PutMapping("/update")
    public Return updateReturn(@RequestBody Return treturn) {

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

    @PutMapping("/assignReturnToInvestment/{idR}/{id}")
    public ResponseEntity<Return> assignReturnToInvestment(@PathVariable("idR") Long idR, @PathVariable("id") Long id,
                                                           @RequestParam("loanDuration") long loanDuration,
                                                           @RequestParam("loanAmount") float loanAmount,
                                                           @RequestParam("interest") float interest)
    {
        Return updatedReturn = iReturnServices.assignReturnToInvestment(idR, id, loanDuration, loanAmount, interest);
        if (updatedReturn != null) {
            return ResponseEntity.ok(updatedReturn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/calculateMonthlyReturns")
    public float calculateMonthlyReturns(@RequestParam long loanDuration, @RequestParam float loanAmount, @RequestParam float interest) {
        return iReturnServices.calculateMonthlyReturns(loanDuration, loanAmount, interest);
    }

    @PutMapping("/addReturnAndAssignToInvestment/{id}")
    public ResponseEntity<Return> addReturnAndAssignToInvestment(@PathVariable("id") Long id,
                                                                 @RequestBody Return aReturn,
                                                                 @RequestParam("loanDuration") long loanDuration,
                                                                 @RequestParam("loanAmount") float loanAmount,
                                                                 @RequestParam("interest") float interest) {
        Return updatedReturn = iReturnServices.addReturnAndAssignToInvestment(id, aReturn, loanDuration, loanAmount, interest);
        if (updatedReturn != null) {
            return ResponseEntity.ok(updatedReturn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}