package tn.esprit.dima_maak.controllers;


import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.services.IInvestmentServices;

import java.util.List;

@RequestMapping("/investment")
@RestController
@RequiredArgsConstructor
public class InvestmentRestController {


    private final IInvestmentServices iInvestmentServices;

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

    /*@PostMapping("/addInvestmentAndAssignToVenture/{idV}")
    public Investment addInvestmentAndAssignToVenture(@RequestBody Investment investment, @PathVariable Long idV) {
        return iInvestmentServices.addInvestmentAndAssignToVenture(investment, idV);
    }*/

    @PostMapping("/calculateTotalInvestment")
    public ResponseEntity<Float> calculateTotalInvestment(@RequestParam Long purchasedShares, @RequestParam Float sharesPrice, @RequestParam Float amount) {
        Float totalInvestment = iInvestmentServices.calculateTotalInvestment(purchasedShares, sharesPrice, amount);
        return ResponseEntity.ok(totalInvestment);
    }
   /* @GetMapping("/calculateTotalInvestment")
    public float calculateTotalInvestment(
            @RequestParam Long purchasedShares, @RequestParam Float sharesPrice, @RequestParam Float amount) {
        return iInvestmentServices.calculateTotalInvestment(purchasedShares, sharesPrice, amount);
    }*/


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

   /* @PostMapping("/addInvestmentAndAssignToVenture/{idV}")
    public ResponseEntity<byte[]> addInvestmentAndAssignToVenture(@RequestBody Investment investment, @PathVariable Long idV) {
        try {
            byte[] pdfContent = iInvestmentServices.addInvestmentAndAssignToVenture(investment, idV);
            if (pdfContent != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "investment_details.pdf");
                return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Venture not found
            }
        } catch (DocumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error generating PDF
        }
    }*/



    @PostMapping("/{idV}/add")
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





}

