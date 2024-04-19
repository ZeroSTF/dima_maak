package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Complaint;

import java.util.List;

public interface IComplaintService {
    Complaint addComplaint(Complaint complaint);
    Complaint updateComplaint(Complaint complaint);
    Complaint findComplaintById(Long id);
    void deleteComplaint(Long id);
    List<Complaint> getAll();

    public double calculatePercentageOfComplaints(List<Complaint> complaints, boolean status);




}
