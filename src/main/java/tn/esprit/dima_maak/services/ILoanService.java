package tn.esprit.dima_maak.services;

import com.itextpdf.text.DocumentException;
import jakarta.transaction.Transactional;
import tn.esprit.dima_maak.entities.Loan;
import tn.esprit.dima_maak.entities.Simulation;
import tn.esprit.dima_maak.entities.User;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.List;

public interface ILoanService {
    public List<Loan> retrieveLoanperUser(Long iduser);
    public Loan retrieveLoan(Long idLoan);
    public Loan addLoanRequest(Loan loan);
    public void removeLoanRequest(Long idloan);
    public Loan modifyLoanRequest(Loan loan);
    public ByteArrayOutputStream simulateLoan(Loan loan) throws DocumentException;
    @Transactional
    public void UpdateLoanStatus(Long id) throws ParseException;
    public void penalityCalculation();
}
