package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Complaint;
import tn.esprit.dima_maak.services.IComplaintService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/complaint")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@AllArgsConstructor
public class ComplaintRestController {
    private IComplaintService complaintService;
    public List<String> badWords = Arrays.asList("badword1", "badword2", "badword3");

    @PostMapping("/save")
    public ResponseEntity<?> addComplaint(@RequestBody Complaint complaint){
        return complaintService.addComplaint(complaint);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateComplaint(@RequestBody Complaint complaint){
        return complaintService.updateComplaint(complaint);
    }

    @GetMapping("/get/{idComplaint}")
    public Complaint getComplaint(@PathVariable("idComplaint") long idComplaint){
        return complaintService.findComplaintById(idComplaint);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteComplaint(@PathVariable("id") Long id){
        complaintService.deleteComplaint(id);
        return "complaint deleted !";
    }
    @GetMapping("/all")
    public List<Complaint> getAllComplaint(){
        return complaintService.getAll();
    }


    @PostMapping("/evaluate")
    public String evaluateComplaint(@RequestBody String complaint) {
        // Replace bad words with asterisks
        for (String word : badWords) {
            complaint = complaint.replaceAll("(?i)" + word, "***"); // Case-insensitive replacement
        }
        return complaint;
    }
}









