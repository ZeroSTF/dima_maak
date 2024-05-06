package tn.esprit.dima_maak.Configuration;

public class UserScore {

    private Long userId;
    private int investmentCount;

    public UserScore(Long userId, int investmentCount) {
        this.userId = userId;
        this.investmentCount = investmentCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getInvestmentCount() {
        return investmentCount;
    }

    public void setInvestmentCount(int investmentCount) {
        this.investmentCount = investmentCount;
    }
}