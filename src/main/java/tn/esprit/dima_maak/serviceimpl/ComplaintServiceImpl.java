package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Complaint;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.IComplaintRepository;
import tn.esprit.dima_maak.services.IComplaintService;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ComplaintServiceImpl implements IComplaintService {
    private IComplaintRepository complaintRepository;
    @Override
    public Complaint addComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint updateComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint findComplaintById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteComplaint(Long id) {

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






