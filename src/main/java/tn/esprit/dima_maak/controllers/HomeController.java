package tn.esprit.dima_maak.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication Tests")
@RequestMapping("/test")
@CrossOrigin("*")
public class HomeController {
    @GetMapping("/")
    @Operation(description = "Check who the current user is")
    @SecurityRequirement(name = "bearerAuth")
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return "hello, your email is: "+currentUsername;
    }

    @GetMapping("/admin")
   // @PreAuthorize("hasRole('ADMIN')")
    public String secured(){
        return "hello admin";
    }
}
