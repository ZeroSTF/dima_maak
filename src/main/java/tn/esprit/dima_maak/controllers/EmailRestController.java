package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.services.IEmailServices;

@RequestMapping("/mail")
@RestController
public class EmailRestController {


    private final IEmailServices emailService;

    public EmailRestController(IEmailServices emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam(value = "file", required = false) MultipartFile[] file, String to, String[] cc, String subject, String body) {
        return emailService.sendMail(file, to, cc, subject, body);

    }
}