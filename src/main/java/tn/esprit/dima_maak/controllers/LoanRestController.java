package tn.esprit.dima_maak.controllers;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Loan;
import tn.esprit.dima_maak.services.ILoanService;

import java.text.ParseException;
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

    @GetMapping("/SimulationLoan")
    public ResponseEntity<ByteArrayResource> SimulationLoan(@RequestBody Loan loan){

        try {
            // Generate the facture PDF as a byte array
            byte[] facturePdfBytes = loanService.simulateLoan(loan).toByteArray();

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("Simulation.pdf", "Simulation.pdf");

            // Return ResponseEntity with the PDF byte array
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(facturePdfBytes.length)
                    .body(new ByteArrayResource(facturePdfBytes));
        } catch (DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/AcceptLoan")
    public void AcceptLoan(@RequestParam("id") Long id) throws ParseException {
        loanService.UpdateLoanStatus(id);
    }
    //@Scheduled(cron = "0 0 0 * * *")
    @PutMapping("/penaltycalculation")
    public void penalty() {
        loanService.penalityCalculation();
    }



}
