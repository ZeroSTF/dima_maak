package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Loan;
import tn.esprit.dima_maak.repositories.LoanRepository;
import tn.esprit.dima_maak.services.ILoanService;

import java.util.List;
@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

    public LoanRepository LoanRepository;
    @Override
    public List<Loan> retrieveLoanperUser(Long iduser) {
        return LoanRepository.findByUserId(iduser);
    }

    @Override
    public Loan retrieveLoan(Long idLoan) {
        return LoanRepository.findById(idLoan).orElse(null);
    }

    @Override
    public Loan addLoanRequest(Loan loan) {
        return LoanRepository.save(loan);
    }

    @Override
    public void removeLoanRequest(Long idloan) {
        LoanRepository.deleteById(idloan);

    }

    @Override
    public Loan modifyLoanRequest(Loan loan) {
        return LoanRepository.save(loan);
    }
}
