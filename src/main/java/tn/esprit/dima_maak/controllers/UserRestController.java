package tn.esprit.dima_maak.controllers;

import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.services.*;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//@Tag(name = "Gestion User")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    IUserService userService;

    //@Operation(description = "recuperer toutes les user de la base de donnees")
    @GetMapping("/retrieve-all-users")
    public List<User> getUsers() {
        List<User> listUsers = userService.retrieveAllUsers();
        return listUsers;
    }

    //@Operation(description = "recuperer le user de la base de donnees")
    @GetMapping("/retrieve-user/{user-id}")
    public User retrieveUser(@PathVariable("user-id") Long userId) {
        User user = userService.retrieveUser(userId);
        return user;
    }

    //@Operation(description = "ajouter le user de la base de donnees")
    @PostMapping("/add-user")
    public User addUser(@RequestBody User c) {
        User user = userService.addUser(c);
        return user;
    }

    //@Operation(description = "supprimer le user de la base de donnees")
    @DeleteMapping("/remove-user/{user-id}")
    public void removeUser(@PathVariable("user-id") Long userId) {
        userService.removeUser(userId);
    }

    //@Operation(description = "modifier le user de la base de donnees")
    @PutMapping("/modify-user")
    public User modifyUser(@RequestBody User c) {
        User user = userService.modifyUser(c);
        return user;
    }
}