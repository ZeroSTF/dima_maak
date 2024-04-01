package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.DTO.LoginResponseDTO;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.services.*;
import tn.esprit.dima_maak.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl  implements IUserService, UserDetailsService {

    @Autowired
    @Lazy
    UserRepository userRepository;
    @Autowired
    @Lazy
    RoleRepository roleRepository;
    @Lazy
    @Autowired
    PasswordEncoder encoder;
    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    @Lazy
    ITokenService tokenService;
    private static final String UPLOAD_DIR = "uploads/profiles/";
    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User retrieveUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public User addUser(User c) {
        c.setPassword(encoder.encode(c.getPassword()));
        return userRepository.save(c);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User modifyUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("No user by this email exists"));
    }

    @Override
    public User registerUser(User user) {
        //////////////////// Check if the email is unique/////////////////////
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return null;
        }
        //////////////////////////////////////////////////////////////////////
        String encodedPassword = encoder.encode(user.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        user.setPassword(encodedPassword);
        user.setRole(authorities);
        user.setBalance(0f);
        user.setLp(0);
        return userRepository.save(user);
    }

    @Override
    public LoginResponseDTO login(String email, String password) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByEmail(email).get(), token);

        } catch (AuthenticationException e){
            e.printStackTrace();
            return new LoginResponseDTO(null, "");
        }
    }

    @Override
    public User loadUserByEmail(String email) {return userRepository.findByEmail(email).get();}

/////////////////////////////// PROFILE PICTURE UPLOAD LOGIC///////////////////////////////////////////////////////////
    // Method to save profile picture
    public String saveProfilePicture(MultipartFile file) throws IOException {
        // Create a unique file name to prevent conflicts
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        // Create the directory if it doesn't exist
        createUploadDirectoryIfNotExist();

        // Get the file path to save the image
        String filePath = UPLOAD_DIR + fileName;

        // Save the file to the specified location
        Path destPath = Paths.get(filePath);
        Files.copy(file.getInputStream(), destPath);

        return fileName;
    }

    // Helper method to generate a unique file name
    private String generateUniqueFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uuid + extension;
    }

    // Helper method to create upload directory if it doesn't exist
    private void createUploadDirectoryIfNotExist() {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
}