package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Complaint;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.IComplaintRepository;
import tn.esprit.dima_maak.services.IComplaintService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ComplaintServiceImpl implements IComplaintService {
    private IComplaintRepository complaintRepository;
    @Autowired
    ServiceBadworld serviceBadworld;
    @Override
    public ResponseEntity<?> addComplaint(Complaint complaint) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(complaint.getSubject());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");

        if (badWordsTotal == 0) {
            complaintRepository.save(complaint);
            return ResponseEntity.ok().body(" complaint  added ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the post. Please remove them.");
        }

    }

    @Override
    public ResponseEntity<?> updateComplaint(Complaint complaint) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(complaint.getSubject());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");
        String mot =jsonObject.getString("censored_content");
        if (badWordsTotal == 0) {
            complaint.setSubject(mot);
            complaintRepository.save(complaint);
            return ResponseEntity.ok().body(" complaint added ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the post. Please remove them.");
        }

    }

    @Override
    public Complaint findComplaintById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteComplaint(Long id) {
  complaintRepository.deleteById(id);
    }

    @Override
    public List<Complaint> getAll() {
        return (List<Complaint>) complaintRepository.findAll();
    }

    @Override
    public double calculatePercentageOfComplaints(List<Complaint> complaints, boolean status) {
        if (complaints.isEmpty()) {
            return 0.0;
        }

        int totalComplaints = complaints.size();
        int count = 0;

        // Parcourir les plaintes et compter celles avec le statut donné
        for (Complaint complaint : complaints) {
            if (complaint.isStatus() == status) {
                count++;
            }
        }

        // Calcul du pourcentage
        return (count / (double) totalComplaints) * 100.0;
    }


}









