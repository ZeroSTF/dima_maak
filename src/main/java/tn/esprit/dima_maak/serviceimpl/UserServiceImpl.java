package tn.esprit.dima_maak.serviceimpl;



import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.DTO.LoginResponseDTO;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.services.*;
import tn.esprit.dima_maak.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserServiceImpl  implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;
    private final ConfirmationRepository confirmationRepository;
    private final PasswordEncoder encoder;

    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;
    private final ITokenService tokenService;
    public static final String UPLOAD_DIR = "uploads/profiles/";
    private final IEmailService emailService;
    private final INotificationService notificationService;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, ITokenService tokenService, ConfirmationRepository confirmationRepository, IEmailService emailService, INotificationService notificationService, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.confirmationRepository = confirmationRepository;
        this.emailService = emailService;
        this.notificationService = notificationService;
        this.locationRepository=locationRepository;
    }

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
        //////////////////// Check if the email is unique/////////////////////
        if (userRepository.findByEmail(c.getEmail()).isPresent()) {
            return null;
        }
        //////////////////////////////////////////////////////////////////////
        c.setPassword(encoder.encode(c.getPassword()));
        c.setStatus(UStatus.Pending);
        Confirmation confirmation = new Confirmation(c);
        userRepository.save(c);
        confirmationRepository.save(confirmation);
        /////////////////MAILING//////////////////////////
        //emailService.sendSimpleMailMessage(c.getSurname()+ " "+ c.getName(),c.getEmail(),confirmation.getToken());
        emailService.sendHtmlEmail(c.getSurname()+ " "+ c.getName(), c.getEmail(), confirmation.getToken());
        /////////////////////////////////////////////////
        return c;
    }

    @Override
    public void removeUser(Long id) {
        Confirmation c = confirmationRepository.findConfirmationByUser(userRepository.findById(id).get());
        if(c!=null){
            confirmationRepository.delete(c);
        }
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
        user.setStatus(UStatus.Pending);
        user.setRole(authorities);
        user.setBalance(0f);
        user.setLp(0);
        Confirmation confirmation = new Confirmation(user);
        if(user.getAddress()!=null) {
            locationRepository.save(user.getAddress());
        }
        else{
            System.out.println("NO LOCATION PASSED");
        }
        userRepository.save(user);
        confirmationRepository.save(confirmation);
        /////////////////MAILING//////////////////////////
        //emailService.sendSimpleMailMessage(user.getSurname()+ " "+ user.getName(),user.getEmail(),confirmation.getToken());
        emailService.sendHtmlEmail(user.getSurname()+ " "+ user.getName(),user.getEmail(),confirmation.getToken());
        /////////////////////////////////////////////////
        return user;
    }

    @Override
    public LoginResponseDTO login(String email, String password) {
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = tokenService.generateJwt(auth);
            User user = userRepository.findByEmail(email).get();
            notificationService.sendLowBalanceNotification(user, 100);//100 is the random threshold I chose
            return new LoginResponseDTO(user.getSurname()+" "+user.getName(), token);
        } catch (AuthenticationException e){
            //e.printStackTrace();
            return new LoginResponseDTO("No email to return", "Invalid email/password supplied");
        }
    }
    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
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

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmail(confirmation.getUser().getEmail()).get();
        user.setStatus(UStatus.Active);
        confirmationRepository.delete(confirmation);
        userRepository.save(user);
        return Boolean.TRUE;
    }

    ////////////////////////Risk assessment algorithm///////////////////////////////////////////////////////////
    public String assessRisk(Long userId) {
        // Check if user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return "User not found";
        }

        User user = userOptional.get();

        // **Income Score Calculation**
        int incomeScore;
        if (user.getSalary() != null && user.getSalary() >= 4000) {
            incomeScore = 5; // High income
        } else if (user.getSalary() != null && user.getSalary() >= 1000) {
            incomeScore = 3; // Medium income
        } else {
            incomeScore = 1; // Low income (or missing salary data)
        }

        // **Job Stability Score Calculation**
        int jobStabilityScore;
        if (List.of("Doctor", "Engineer", "Teacher").contains(user.getJob())) {
            jobStabilityScore = 3; // Stable job
        } else {
            jobStabilityScore = 1; // Less stable job (assuming all others are less stable)
        }

        // **Debt Score Calculation**
        int debtScore;
        if (user.getBalance() == null || user.getBalance() == 0) {
            debtScore = 5; // No debt
        } else if ((float) user.getBalance() / user.getSalary() < 0.3) {
            debtScore = 3; // Low debt-to-income ratio
        } else {
            debtScore = 1; // High debt-to-income ratio
        }

        // **Calculate Total Score with Weights**
        float totalScore = (float) (incomeScore + jobStabilityScore * 2 + debtScore * 1.5);

        // **Determine Risk Category based on Score**
        System.out.println("TOTAL SCORE IS: " +totalScore);
        return determineRiskCategory(totalScore);
    }

    private String determineRiskCategory(float totalScore) {
        //I calculated the max to be 18.5 and the median to be 14
        if (totalScore >= 15.5) {
            return "Low Risk";
        } else if (totalScore >= 9.25) {
            return "Medium Risk";
        } else {
            return "High Risk";
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public String generateAffiliateLink(User user){
            return "http://localhost:8080/register/"+user.getId();

    }

}