package tn.esprit.dima_maak.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.dima_maak.entities.Complaint;

import java.util.List;

public interface IComplaintService {
    ResponseEntity<?> addComplaint(Complaint complaint);
    public ResponseEntity<?> updateComplaint(Complaint complaint);
    Complaint findComplaintById(Long id);
    void deleteComplaint(Long id);
    List<Complaint> getAll();

    public double calculatePercentageOfComplaints(List<Complaint> complaints, boolean status);





}
