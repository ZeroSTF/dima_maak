package tn.esprit.dima_maak.controllers;


import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.Configuration.UserScore;
import tn.esprit.dima_maak.entities.IStatus;
import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.entities.Venture;
import tn.esprit.dima_maak.repositories.IInvestmentRepository;
import tn.esprit.dima_maak.repositories.IVentureRepository;
import tn.esprit.dima_maak.services.IInvestmentServices;

import java.util.List;
import java.util.Map;

@RequestMapping("/investment")
@RestController
@RequiredArgsConstructor
public class InvestmentRestController {


    private final IInvestmentServices iInvestmentServices;
    private final IInvestmentRepository investmentRepository;
    private final IVentureRepository ventureRepository;


    @PostMapping("/add")
    public Investment addInvestment(@RequestBody Investment investment) {

        return iInvestmentServices.addInvestment(investment);

    }

    @PutMapping("/update")
    public Investment updateInvestment(@RequestBody Investment investment) {

        return iInvestmentServices.updateInvestment(investment);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInvestment(@PathVariable("id") Long id) {
        boolean deleted = iInvestmentServices.deleteInvestment(id);
        if (deleted) {
            return ResponseEntity.ok("Investment deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investment with ID " + id + " does not exist.");
        }
    }

    @GetMapping("/get/{id}")
    public Investment getInvestmentById(@PathVariable("id") Long id) {
        return iInvestmentServices.getInvestmentById(id);
    }

    @GetMapping("/getAllInvestment")
    public List<Investment> getVenture() {
        return ResponseEntity.ok().body(iInvestmentServices.getAllInvestment()).getBody();

    }

    @PutMapping("/assignInvestmentToVenture/{id}/{idV}")
    public Investment assignInvestmentToVenture(@PathVariable("id") Long id, @PathVariable("idV") Long idV) {

        return iInvestmentServices.assignInvestmentToVenture(id, idV);

    }


    @PutMapping("/doInvestment/{investmentId}/{ventureId}")
    public String doInvestment(@PathVariable Long investmentId, @PathVariable Long ventureId) {
        Investment investment = investmentRepository.findById(investmentId).orElse(null);
        Venture venture = ventureRepository.findById(ventureId).orElse(null);

        if (investment == null || venture == null) {
            return "The investment opportunity or venture is unavailable.";
        }

        if (venture.getStatus() == IStatus.CLOSED) {
            return "You cannot invest as the venture is closed";
        }

        long purchasedShares = investment.getPurchasedShares();
        long availableShares = venture.getAvailableShares() != null ? venture.getAvailableShares() - purchasedShares : 0;
        float investmentAmount = investment.getAmount();
        float loanAmount = venture.getLoanAmount() != null ? venture.getLoanAmount() - investmentAmount : 0;

        if (availableShares < 0 || loanAmount < 0) {
            return "High investment amount";
        }

        // Update availableShares and loanAmount
        venture.setAvailableShares(availableShares);
        venture.setLoanAmount(loanAmount);

        // Update status to CLOSED if both availableShares and loanAmount are 0
        if (venture.getAvailableShares() == 0 && venture.getLoanAmount() == 0) {
            venture.setStatus(IStatus.CLOSED);
        }

        investment.setVenture(venture);

        investment = investmentRepository.save(investment);
        venture = ventureRepository.save(venture);

        return "The investment has been successfully allocated to the venture.";
    }


    /* @PostMapping("/AddAndDoInvestment/{idV}")
    public Investment AddAndDoInvestment(@RequestBody Investment investment, @PathVariable Long idV) {
        return iInvestmentServices.AddAndDoInvestment(investment, idV);
    }*/

    @PutMapping("/AddAndDoInvestment/{idV}")
    public String AddAndDoInvestment(@RequestBody Investment investment, @PathVariable Long idV) {
        String resultMessage = iInvestmentServices.AddAndDoInvestment(investment, idV);
        return resultMessage;
    }




    @PostMapping("/calculateTotalInvestment")
    public ResponseEntity<Float> calculateTotalInvestment(@RequestParam Long purchasedShares, @RequestParam Float sharesPrice, @RequestParam Float amount) {
        Float totalInvestment = iInvestmentServices.calculateTotalInvestment(purchasedShares, sharesPrice, amount);
        return ResponseEntity.ok(totalInvestment);
    }

    @GetMapping("/users/{id}/investments")
    public List<Investment> getUserInvestments(@PathVariable Long id) {
        return iInvestmentServices.getUserInvestments(id);
    }

    @GetMapping("/generatePDF")
    public ResponseEntity<byte[]> generatePDF() {
        try {
            Investment investment = new Investment(); // Supposons que vous ayez une instance d'Investment ici
            byte[] pdfBytes = iInvestmentServices.generateInvestmentPDF(investment);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "investment_details.pdf");
            headers.setContentLength(pdfBytes.length);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (DocumentException e) {
            e.printStackTrace(); // Gérez l'erreur de manière appropriée
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{idV}/addInvestmentAndAssignToVenture-generatepdf&totalInvest")
    public ResponseEntity<byte[]> addInvestmentAndAssignToVenture(@RequestBody Investment investment, @PathVariable Long idV) {
        try {
            byte[] pdfBytes = iInvestmentServices.addInvestmentAndAssignToVenture(investment, idV);
            if (pdfBytes != null) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=investment.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DocumentException e) {
            // Gérer l'exception de génération de document
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/scores")
    public List<UserScore> getUserScores() {
        return iInvestmentServices.calculateUserScores();
    }

    //////////////////////////////////////////
   /* @GetMapping("/return-statistics")
    public Map<User, Investment.ReturnStats> getReturnStatisticsByUserId() {
        return iInvestmentServices.getReturnStatisticsByUserId();
    }*/

}








