package tn.esprit.dima_maak.services;

public interface IEmailService {
    void sendSimpleMailMessage(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
}
