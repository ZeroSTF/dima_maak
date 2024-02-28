package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Investment;

import java.util.List;

public interface IInvestmentServices {

    Investment addInvestment(Investment investment);
    Investment updateInvestment(Investment investment);
    boolean deleteInvestment (Long idR);
    List<Investment> getAllInvestment();
    Investment getInvestmentById(Long id);

}
