package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.DTO.LoginDTO;
import tn.esprit.dima_maak.DTO.LoginResponseDTO;
import tn.esprit.dima_maak.DTO.LogoutResponseDTO;
import tn.esprit.dima_maak.DTO.RegistrationDTO;
import tn.esprit.dima_maak.entities.Loyalty;
import tn.esprit.dima_maak.entities.Reason;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.services.ILoyaltyService;
import tn.esprit.dima_maak.services.IUserService;
import java.time.LocalDateTime;

@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthenticationController {
    private final IUserService userService;
    private final ILoyaltyService loyaltyService;
    @Operation(description = "register a user")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO body){
        User user = new User(body.getCin(), body.getName(), body.getSurname(), body.getAddress(), body.getBirthDate(), body.getEmail(), body.getPassword(), body.getSalary(), body.getJob(), body.getRib());
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already in use");
        }
    }

    @Operation(description = "register a user with affiliate link")
    @PostMapping("/register/{id-user}")
    public ResponseEntity<?> registerUserWithAffiliateLink(@RequestBody RegistrationDTO body,@PathVariable("id-user") Long userId){
        User user = new User(body.getCin(), body.getName(), body.getSurname(), body.getAddress(), body.getBirthDate(), body.getEmail(), body.getPassword(), body.getSalary(), body.getJob(), body.getRib());
        User registeredUser = userService.registerUser(user);
        User affiliateUser = userService.retrieveUser(userId);
        affiliateUser.setLp(affiliateUser.getLp()+5);
        Loyalty loyalty = new Loyalty(null, LocalDateTime.now(), Reason.Recruitment, 5L, affiliateUser);
        userService.modifyUser(affiliateUser);
        loyaltyService.addLoyalty(loyalty);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already in use");
        }
    }

    @Operation(description = "login")
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginDTO body){
        return userService.login(body.getEmail(), body.getPassword());
    }
    @Operation(description = "logout")
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout() {
        try {
            userService.logout(); // Perform logout logic
            return ResponseEntity.ok(new LogoutResponseDTO(true, "Logout successful!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LogoutResponseDTO(false, e.getMessage()));
        }
    }
}
