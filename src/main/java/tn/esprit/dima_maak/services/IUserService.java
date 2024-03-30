package tn.esprit.dima_maak.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.dima_maak.entities.*;
import java.util.List;

public interface IUserService extends UserDetailsService {
    public List<User> retrieveAllUsers();
    public User retrieveUser(Long id);
    public User addUser(User c);
    public void removeUser(Long id);
    public User modifyUser(User User);
    public UserDetails loadUserByUsername(String email);
}