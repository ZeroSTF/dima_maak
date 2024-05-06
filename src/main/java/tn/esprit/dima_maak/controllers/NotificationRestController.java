package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Notification;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.services.INotificationService;
import tn.esprit.dima_maak.services.IUserService;

import java.util.List;

@Tag(name = "Notification management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class NotificationRestController {
    private final INotificationService notificationService;
    private final IUserService userService;

    ///////////////////////////////////////////////////////////ADMIN DASHBOARD RELATED WORK/////////////////////////////////////////
    @Operation(description = "get all notifications")
    @GetMapping("/getAll")
    public List<Notification> getNotifications() {
        return notificationService.retrieveAllNotifications();
    }

    @Operation(description = "get one notification")
    @GetMapping("/get/{notification-id}")
    public Notification retrieveNotification(@PathVariable("notification-id") Long notificationId) {
        return notificationService.retrieveNotification(notificationId);
    }

    @Operation(description = "add a notification")
    @PostMapping("/add")
    public Notification addNotification(@RequestBody Notification c) {
        return notificationService.addNotification(c);
    }

    @Operation(description = "delete a notification")
    @DeleteMapping("/delete/{notification-id}")
    public void removeNotification(@PathVariable("notification-id") Long notificationId) {
        notificationService.removeNotification(notificationId);
    }

    @Operation(description = "edit a notification")
    @PutMapping("/update")
    public Notification modifyNotification(@RequestBody Notification c) {
        return notificationService.modifyNotification(c);
    }

    @Operation(description = "Get current user's unread notifications")
    @GetMapping("/getUnread")
    public ResponseEntity<?> getUnread() {
        User currentUser;
        try {
            ////////////retrieving current user/////////////////////////////////
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()) ;
        }
        return ResponseEntity.ok().body(notificationService.getUnread(currentUser));
    }

    @Operation(description = "Get all of the current user's notifications")
    @GetMapping("/getAllByUser")
    public ResponseEntity<?> getAllByUser() {
        User currentUser;
        try {
            ////////////retrieving current user/////////////////////////////////
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            currentUser = userService.loadUserByEmail(currentEmail);
            ////////////////////////////////////////////////////////////////////
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()) ;
        }
        return ResponseEntity.ok().body(notificationService.getByUser(currentUser));
    }
    
}
