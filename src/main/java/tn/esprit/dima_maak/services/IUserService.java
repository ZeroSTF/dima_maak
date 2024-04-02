package tn.esprit.dima_maak.services;

import tn.esprit.dima_maak.entities.*;
import java.util.List;

public interface IUserService {
    public List<User> retrieveAllUsers();

    public User retrieveUser(Long id);

    public User addUser(User c);

    public void removeUser(Long id);

    public User modifyUser(User User);

    void updateBalance(Long id, float returnAmount, float returnInterest, long sharesGain, float totalInvestment);
    public boolean hasInvestments(Long id);

}