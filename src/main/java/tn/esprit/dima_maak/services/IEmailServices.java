package tn.esprit.dima_maak.services;

import org.springframework.web.multipart.MultipartFile;

public interface IEmailServices {
    String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);
}