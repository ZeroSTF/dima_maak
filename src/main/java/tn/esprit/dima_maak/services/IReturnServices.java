package tn.esprit.dima_maak.services;
import tn.esprit.dima_maak.entities.Return;



import java.util.List;

public interface IReturnServices {

    Return addReturn(Return treturn);
    Return updateReturn(Return treturn);
    boolean deleteReturn (Long idR);
    List<Return> getAllReturn();
    Return getReturnById(Long idR);
    Return assignReturnToInvestment(Long idR, Long id, long loanDuration, float loanAmount, float interest);
    float calculateMonthlyReturns(long loanDuration, float loanAmount, float interest);
    Return addReturnAndAssignToInvestment(Long id, Return aReturn, long loanDuration, float loanAmount, float interest);




}