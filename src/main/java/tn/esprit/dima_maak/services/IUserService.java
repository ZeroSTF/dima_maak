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
    public void removeUser(Long id);
    public User modifyUser(User User);
    public User registerUser(User user);
    public LoginResponseDTO login(String email, String password);
    public void logout();
    public User loadUserByEmail(String email);
    public String saveProfilePicture(MultipartFile file)throws IOException;
    public Boolean verifyToken(String token);
    public String assessRisk(Long userId);
    public String generateAffiliateLink(User user);

    ///KHEDMET RAMI
    void updateBalance(Long id, float returnAmount, float returnInterest, long sharesGain, float totalInvestment);
    public boolean hasInvestments(Long id);
}