package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.services.IUserService;

import java.util.List;


@Tag(name = "User management")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
//@CrossOrigin("*")
public class UserRestController {
    IUserService userService;

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
    public void removeUser(@PathVariable("user-id") Long userId) {
        userService.removeUser(userId);
    }

    @Operation(description = "edit a user")
    @PutMapping("/update")
    public User modifyUser(@RequestBody User c) {
        return userService.modifyUser(c);
    }
}