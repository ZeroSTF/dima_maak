package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.services.IUserService;

import java.io.IOException;
import java.util.List;


@Tag(name = "User management")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin("*")
public class UserRestController {
    IUserService userService;

    ///////////////////////////////////////////////////////////ADMIN DASHBOARD RELATED WORK/////////////////////////////////////////
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

    ///////////////////////////////////////////////////////////////PROFILE RELATED WORK (IN PROGRESS)//////////////////////////////////////////////////////
    @Operation(description = "update your own profile")
    @PutMapping("/updateProfile")
    public ResponseEntity<?> modifyProfile(@RequestBody User c) {
        ////////////retrieving current user/////////////////////////////////
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User currentUser = userService.loadUserByEmail(currentEmail);
        ////////////////////////////////////////////////////////////////////

        if (currentUser != null && currentUser.getId().equals(c.getId())) {
            userService.modifyUser(c);
            return ResponseEntity.ok("Profile updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
    }

    @PostMapping("/upload")
    @Operation(description = "upload your own profile picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        // Check if file is empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        try {
            ////////////retrieving current user/////////////////////////////////
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentEmail = authentication.getName();
            User currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
            String fileName = userService.saveProfilePicture(file);
            currentUser.setPhoto(fileName);
            userService.modifyUser(currentUser);
            return ResponseEntity.ok().body("Profile picture uploaded successfully: "
                    + currentUser.getSurname()+" "
                    + currentUser.getName()
                    +", your profile picture file name is: "
                    +currentUser.getPhoto());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile picture");
        }
    }

}