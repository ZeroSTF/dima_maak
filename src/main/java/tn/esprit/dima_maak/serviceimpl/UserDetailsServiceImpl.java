package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private PasswordEncoder encoder;
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("No user by this email exists"));
    }
}
