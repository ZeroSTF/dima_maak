package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.DTO.LoginDTO;
import tn.esprit.dima_maak.DTO.LoginResponseDTO;
import tn.esprit.dima_maak.DTO.RegistrationDTO;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.serviceimpl.UserServiceImpl;
import tn.esprit.dima_maak.services.IUserService;

@Tag(name = "Authentication")
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Lazy
    IUserService userService;
    @Operation(description = "register a user")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO body){
        User user = new User(null, body.getCin(), body.getName(), body.getSurname(), body.getAddress(), body.getBirthDate(), body.getEmail(), body.getPassword(), body.getSalary(), body.getJob(), null, null, body.getRib(), null, null, null, null, null, null);
        User registeredUser = userService.registerUser(user);
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
}
