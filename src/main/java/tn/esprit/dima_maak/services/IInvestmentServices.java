package tn.esprit.dima_maak.services;

import com.lowagie.text.DocumentException;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.dima_maak.Configuration.UserScore;
import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.entities.User;

import java.util.List;
import java.util.Map;

public interface IInvestmentServices {

    Investment addInvestment(Investment investment);

    Investment updateInvestment(Investment investment);

    boolean deleteInvestment(Long id);

    List<Investment> getAllInvestment();
     //List<Investment> getAllInvestment();

    Investment getInvestmentById(Long id);
    Investment assignInvestmentToVenture(Long id, Long idV);
    Float calculateTotalInvestment(Long purchasedShares, Float sharesPrice, Float amount);
    List<Investment> getUserInvestments(Long id);
    byte[] generateInvestmentPDF(Investment investment) throws DocumentException;
     byte[] addInvestmentAndAssignToVenture(Investment investment, Long idV) throws DocumentException;
     List<UserScore> calculateUserScores();
    public String doInvestment(@PathVariable Long investmentId, @PathVariable Long ventureId);
   /* public Investment AddAndDoInvestment(Investment investment, Long idV);*/
   public String AddAndDoInvestment(Investment investment, Long idV);
   String addInvestmentAndAssignToVentureAndUser(Investment investment, Long idV, Long userId);



        /*public Map<User, Investment.ReturnStats> getReturnStatisticsByUserId();*/
    //Investment assignInvestmentToInvestor(Long id, Long idU);

}