package tn.esprit.dima_maak.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Notification;
import tn.esprit.dima_maak.services.INotificationService;

import java.util.List;

@Tag(name = "Notification management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationRestController {
    private final INotificationService notificationService;

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
    
}
