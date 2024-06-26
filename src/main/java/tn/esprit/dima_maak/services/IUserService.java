package tn.esprit.dima_maak.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.DTO.LoginResponseDTO;
import tn.esprit.dima_maak.entities.*;
import java.io.IOException;
import java.util.List;

public interface IUserService {
    public List<User> retrieveAllUsers();
    public User retrieveUser(Long id);
    public User addUser(User c);
    public void removeUser(Long id) throws IOException;
    public User modifyUser(User User);
    public User registerUser(User user);
    public LoginResponseDTO login(String email, String password);
    public void logout();
    public User loadUserByEmail(String email);
    public String saveProfilePicture(MultipartFile file)throws IOException;
    void deleteProfilePicture(String fileName) throws IOException;
    public Boolean verifyToken(String token);
    public String assessRisk(Long userId);
    public String generateAffiliateLink(User user);

    //User statics by salary
    int[] countUsers ();

    //User statistics by age
    int[] countUsersByAge();

    //User statistics by location
    List<Object[]> findAllUserCoordinates();

    //Scheduled discount notification
    public void notifyUsers();

    ///KHEDMET RAMI
    void updateBalance(Long id, float returnAmount, float returnInterest, long sharesGain, float totalInvestment);
    public boolean hasInvestments(Long id);
}