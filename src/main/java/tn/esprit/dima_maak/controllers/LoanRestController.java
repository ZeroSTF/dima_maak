package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Loan;
import tn.esprit.dima_maak.services.ILoanService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/loan")
public class LoanRestController {
    ILoanService loanService;

    @GetMapping("/GetLoansbyUserId/{iduser}")
  public  List<Loan> GetLoansbyUserId(@PathVariable("iduser") Long iduser){
        return loanService.retrieveLoanperUser(iduser);


    }
    @GetMapping("/GetLoan/{idloan}")
    public   Loan GetLoan(@PathVariable("idloan") Long idloan){
        return loanService.retrieveLoan(idloan);


    }

    @PostMapping("/addLoan")
    public   Loan AddLoan(@RequestBody Loan loan){
        return loanService.addLoanRequest(loan);


    }
    @DeleteMapping("/DeleteLoan/{idloan}")
    public   void RemoveLoan(@PathVariable ("idloan") Long  idloan){
         loanService.removeLoanRequest(idloan);


    }
    @PutMapping("/ModifyLoanRequest")
    public  Loan UpdateLoan(@RequestBody  Loan  loan){
       return loanService.modifyLoanRequest(loan);


    }

}
