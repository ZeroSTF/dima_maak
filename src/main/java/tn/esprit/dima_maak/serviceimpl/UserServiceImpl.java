package tn.esprit.dima_maak.serviceimpl;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final InsurancePRepository insurancePRepository;
    private final PasswordEncoder encoder;

    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;
    private final ITokenService tokenService;
    public static final String UPLOAD_DIR = "uploads/profiles/";
    private final IEmailService emailService;
    private final INotificationService notificationService;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, ITokenService tokenService, ConfirmationRepository confirmationRepository, IEmailService emailService, INotificationService notificationService, LocationRepository locationRepository, InsurancePRepository insurancePRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.confirmationRepository = confirmationRepository;
        this.emailService = emailService;
        this.notificationService = notificationService;
        this.locationRepository=locationRepository;
        this.insurancePRepository=insurancePRepository;
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
        Confirmation confirmation = new Confirmation(c);
        Role userRole = roleRepository.findById(c.getRole().stream().findFirst().get().getId()).get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        c.setRole(authorities);
        c.setCreditScore(0f);
        if(c.getAddress()!=null) {
            locationRepository.save(c.getAddress());
        }
        else{
            System.out.println("NO LOCATION PASSED");
        }
        userRepository.save(c);
        confirmationRepository.save(confirmation);
        /////////////////MAILING//////////////////////////
        //emailService.sendSimpleMailMessage(c.getSurname()+ " "+ c.getName(),c.getEmail(),confirmation.getToken());
        emailService.sendHtmlEmail(c.getSurname()+ " "+ c.getName(), c.getEmail(), confirmation.getToken());
        /////////////////////////////////////////////////
        return c;
    }

    @Override
    public void removeUser(Long id) throws IOException {
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            if(!user.getPhoto().equals("default.jpg")){
                this.deleteProfilePicture(user.getPhoto());
            }
            Confirmation c = confirmationRepository.findConfirmationByUser(user);
            if(c != null){
                if(!"default.jpg".equals(user.getPhoto())){
                    this.deleteProfilePicture(user.getPhoto());
                }
                confirmationRepository.delete(c);
            }
            userRepository.deleteById(id);
        }
    }

    @Override
    public User modifyUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            notificationService.sendProfileEditNotification(user);
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
        Role userRole = roleRepository.findById(2L).get();
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
    @Override
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
    @Override
    public void deleteProfilePicture(String fileName) throws IOException {
        // Construct the file path
        String filePath = UPLOAD_DIR + fileName;

        // Create a Path object for the file
        Path path = Paths.get(filePath);

        // Check if the file exists
        if (Files.exists(path)) {
            // Delete the file
            Files.delete(path);
            System.out.println("Profile picture deleted successfully: " + fileName);
        } else {
            // File doesn't exist
            System.out.println("Profile picture not found: " + fileName);
        }
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
            return "http://localhost:4200/signup/"+user.getId();
    }

    public int[] countUsers (){
        int[] counts = new int[5];
        counts[0] = userRepository.countUsers();
        counts[1] = userRepository.countUsersWithSalaryLessThan1000();
        counts[2] = userRepository.countUsersWithSalaryBetween1000And3000();
        counts[3] = userRepository.countUsersWithSalaryBetween3000And6000();
        counts[4] = userRepository.countUsersWithSalaryMoreThan6000();
        return counts;
    }

    //User statistics by age
    public int[] countUsersByAge(){
        int[] counts = new int[4];
        counts[0] = userRepository.countUsersBelow18();
        counts[1] = userRepository.countUsersBetween18And25();
        counts[2] = userRepository.countUsersBetween25And40();
        counts[3] = userRepository.countUsersAbove40();
        return counts;
    }

    //User statistics by location
    public List<Object[]> findAllUserCoordinates(){
        return userRepository.findAllUserCoordinates();
    }

    //Discount notification the first of every month
    @Scheduled(cron = "1 1 1 1 * ?")
    public void notifyUsers(){
        //old man notification
        //if 20% of users are above 40
        if(userRepository.countUsersAbove40() > userRepository.countUsers()*0.2){
            notificationService.sendHealthDiscountNotification();
            List<InsuranceP> insurancePs=insurancePRepository.findByType(IType.Health_Insurance);
            insurancePs.forEach(insuranceP -> {
                insuranceP.setPremium(insuranceP.getPremium()*0.9f);
                insurancePRepository.save(insuranceP);
            });
        }
        if(userRepository.countUsersByJob("Farmer")+userRepository.countUsersByJob("Farm Owner")>userRepository.countUsers()*0.2){
            notificationService.sendAgricultureDiscountNotification();
            List<InsuranceP> insurancePs=insurancePRepository.findByType(IType.Agriculteur_Insurance);
            insurancePs.forEach(insuranceP -> {
                insuranceP.setPremium(insuranceP.getPremium()*0.9f);
                insurancePRepository.save(insuranceP);
            });
        }
    }

    //////KHEDMET RAMI
    @Transactional
    public void updateBalance(Long id, float returnAmount, float returnInterest, long sharesGain, float totalInvestment) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Vérifier si l'utilisateur a des investissements associés
            if (!user.getInvestments().isEmpty()) {
                float newBalance = user.getBalance() + returnAmount + returnInterest + sharesGain - totalInvestment;
                user.setBalance(newBalance);
                userRepository.save(user);
            }
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            // Vous pouvez lancer une exception ou gérer d'une autre manière selon vos besoins
        }
    }

    public boolean hasInvestments(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null && !user.getInvestments().isEmpty();
    }

}