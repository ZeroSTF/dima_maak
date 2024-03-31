package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.Investment;

import java.util.List;

public interface IInvestmentServices {

    Investment addInvestment(Investment investment);

    Investment updateInvestment(Investment investment);

    boolean deleteInvestment(Long id);

    List<Investment> getAllInvestment();

    Investment getInvestmentById(Long id);
    Investment assignInvestmentToVenture(Long id, Long idV);
    Float calculateTotalInvestment(Long purchasedShares, Float sharesPrice, Float amount);
     Investment addInvestmentAndAssignToVenture(Investment investment, Long idV);
}