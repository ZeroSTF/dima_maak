package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Loan;
import tn.esprit.dima_maak.entities.User;

import java.util.List;

public interface ILoanService {
    public List<Loan> retrieveLoanperUser(Long iduser);
    public Loan retrieveLoan(Long idLoan);
    public Loan addLoanRequest(Loan loan);
    public void removeLoanRequest(Long idloan);
    public Loan modifyLoanRequest(Loan loan);
}
