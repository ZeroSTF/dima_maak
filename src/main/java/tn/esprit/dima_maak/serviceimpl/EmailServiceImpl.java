package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.services.IEmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender emailSender;
    @Override
    public void sendSimpleMailMessage(String name, String to, String token) {
        try{
            ////////TODO work on the mailing//////////////
        } catch(Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
