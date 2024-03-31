package tn.esprit.dima_maak.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.dima_maak.DTO.LoginResponseDTO;
import tn.esprit.dima_maak.entities.*;
import java.util.List;

public interface IUserService {
    public List<User> retrieveAllUsers();
    public User retrieveUser(Long id);
    public User addUser(User c);
    public void removeUser(Long id);
    public User modifyUser(User User);
    public User registerUser(User user);
    public LoginResponseDTO login(String email, String password);
}