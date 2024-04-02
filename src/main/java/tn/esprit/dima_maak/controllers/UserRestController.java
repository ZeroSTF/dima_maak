package tn.esprit.dima_maak.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Tag(name = "User management")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    IUserService userService;

    @Operation(description = "get all users")
    @GetMapping("/retrieve-all-users")
    public List<User> getUsers() {
        List<User> listUsers = userService.retrieveAllUsers();
        return listUsers;
    }

    @Operation(description = "get one user")
    @GetMapping("/retrieve-user/{user-id}")
    public User retrieveUser(@PathVariable("user-id") Long userId) {
        User user = userService.retrieveUser(userId);
        return user;
    }

    @Operation(description = "add a user")
    @PostMapping("/add-user")
    public User addUser(@RequestBody User c) {
        User user = userService.addUser(c);
        return user;
    }

    @Operation(description = "delete a user")
    @DeleteMapping("/remove-user/{user-id}")
    public void removeUser(@PathVariable("user-id") Long userId) {
        userService.removeUser(userId);
    }

    @Operation(description = "edit a user")
    @PutMapping("/modify-user")
    public User modifyUser(@RequestBody User c) {
        User user = userService.modifyUser(c);
        return user;
    }

    @PutMapping("/users/{id}/updateBalance")
    public ResponseEntity<String> updateBalance(@PathVariable Long id,
                                                @RequestParam float returnAmount,
                                                @RequestParam float returnInterest,
                                                @RequestParam long sharesGain,
                                                @RequestParam float totalInvestment) {
        boolean hasInvestments = userService.hasInvestments(id); // Vérifier si l'utilisateur a des investissements

        if (!hasInvestments) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have any associated investments.");
        }

        userService.updateBalance(id, returnAmount, returnInterest, sharesGain, totalInvestment);
        return ResponseEntity.ok("Balance updated successfully.");
    }
}
