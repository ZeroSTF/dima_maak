package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.services.IUserService;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static tn.esprit.dima_maak.serviceimpl.UserServiceImpl.UPLOAD_DIR;


@Tag(name = "User management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserRestController {
    private final IUserService userService;
    private final ResourceLoader resourceLoader;

    ///////////////////////////////////////////////////////////ADMIN DASHBOARD RELATED WORK/////////////////////////////////////////
    @Operation(description = "get all users")
    @GetMapping("/retrieve-all-users")
    public List<User> retrieveAllUsers() {
        List<User> listUsers = userService.retrieveAllUsers();
        return listUsers;
      
    @Operation(description = "get all users")
    @GetMapping("/getAll")
    public List<User> getUsers() {
        return userService.retrieveAllUsers();
    }

    @Operation(description = "get one user")
    @GetMapping("/get/{user-id}")
    public User retrieveUser(@PathVariable("user-id") Long userId) {
        return userService.retrieveUser(userId);
    }

    @Operation(description = "add a user")
    @PostMapping("/add")
    public User addUser(@RequestBody User c) {
        return userService.addUser(c);
    }

    @Operation(description = "delete a user")
    @DeleteMapping("/delete/{user-id}")
    public void removeUser(@PathVariable("user-id") Long userId) throws IOException {
        userService.removeUser(userId);
    }

    @Operation(description = "edit a user")
    @PutMapping("/update")
    public User modifyUser(@RequestBody User c) {
        return userService.modifyUser(c);
    }

    @Operation(description = "confirm account")
    @GetMapping
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String token) {
        Boolean isSuccess = userService.verifyToken(token);
        if (isSuccess) {
            return ResponseEntity.ok("Account confirmed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    @Operation(description = "assess user risk")
    @GetMapping("/assess/{user-id}")
    public ResponseEntity<?> assessUserRisk(@PathVariable("user-id") Long userId) {
        String riskCategory;
        try {
            riskCategory = userService.assessRisk(userId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error assessing risk: " + e.getMessage());
        }

        return ResponseEntity.ok(riskCategory);
    }


    ///////////////////////////////////////////////////////////////PROFILE RELATED WORK (IN PROGRESS?)//////////////////////////////////////////////////////
    @PostMapping("/upload/{user-id}")
    @Operation(description = "upload your own profile picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable("user-id") Long userId) {
        // Check if file is empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        try {
            User user=userService.retrieveUser(userId);
            String fileName = userService.saveProfilePicture(file);
            if(!user.getPhoto().equals("default.jpg")){
                userService.deleteProfilePicture(user.getPhoto());
            }
            user.setPhoto(fileName);
            userService.modifyUser(user);
            return ResponseEntity.ok().body("Profile picture uploaded successfully: "
                    + user.getSurname() + " "
                    + user.getName()
                    + ", your profile picture file name is: "
                    + user.getPhoto());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(description = "generate affiliate link")
    @GetMapping("/generateAffiliateLink")
    public ResponseEntity<String> generateAffiliateLink() {
        ////////////retrieving current user/////////////////////////////////
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User currentUser = userService.loadUserByEmail(currentEmail);
        ////////////////////////////////////////////////////////////////////
        if (currentUser != null) {
            return ResponseEntity.ok(userService.generateAffiliateLink(currentUser));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
    }

    @Operation(description = "Get the current user")
    @GetMapping("/current")
    public ResponseEntity<?> getCurrent() {
        User currentUser;
        try {
            ////////////retrieving current user/////////////////////////////////
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(currentUser);
    }

    @Operation(description = "Get photo")
    @GetMapping("/getPhoto/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        String filePath = UPLOAD_DIR + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }
        byte[] imageData = Files.readAllBytes(file.toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    //user statistics by salary
    @Operation(description="Count users based on salary")
    @GetMapping("/countUsers")
    public int[] countUsers() {
        return userService.countUsers();
    }

    //user statistics by age
    @Operation(description="Count users based on age")
    @GetMapping("/countUsersByAge")
    public int[] countUsersByAge() {
        return userService.countUsersByAge();
    }
    //user statistics by location
    @Operation(description="Get all user coordinates")
    @GetMapping("/findAllUserCoordinates")
    public List<Object[]> findAllUserCoordinates() {
        return userService.findAllUserCoordinates();
    }


    //////KHEDMET RAMI
    @PutMapping("/users/{id}/updateBalance")
    public ResponseEntity<String> updateBalance(@PathVariable Long id, @RequestParam float returnAmount, @RequestParam float returnInterest, @RequestParam long sharesGain, @RequestParam float totalInvestment) {
        boolean hasInvestments = userService.hasInvestments(id); // Vérifier si l'utilisateur a des investissements
        if (!hasInvestments) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have any associated investments.");
        }
        userService.updateBalance(id, returnAmount, returnInterest, sharesGain, totalInvestment);
        return ResponseEntity.ok("Balance updated successfully.");
    }

}

