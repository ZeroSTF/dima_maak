package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Demande;
import tn.esprit.dima_maak.entities.Leasing;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.IDemandeRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.serviceimpl.PdfService;
import tn.esprit.dima_maak.services.ILeasingService;
import tn.esprit.dima_maak.services.IUserService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/leasings")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class LeasingRestController {

    private final ILeasingService leasingService;
    private  final UserRepository userRepository ;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        List<User> users = userRepository.findUsersByEmail(email);
        if (!users.isEmpty()) {
            for (User user : users) {
                if (password.equals(user.getPassword())) {
                    return ResponseEntity.ok(user);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @Autowired
    private PdfService pdfService;
    @Autowired
    private IDemandeRepository iDemandeRepository;

    @PostMapping("/add")
    public Leasing createLeasing(@RequestBody Leasing leasing, @RequestParam("iduser") Long iduser, @RequestParam("iddemende") Long iddemande) throws IOException, MessagingException, IOException {
        Leasing createdLeasing = leasingService.createLeasing(leasing, iduser, iddemande);
        Demande iDemandey = iDemandeRepository.findById(iddemande).get();
        List<String> contents = new ArrayList<>();
        contents.add("Contrat :");
        contents.add("Leasing Information:");
        contents.add("Start Date: " + leasing.getStartdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        contents.add("End Date: " + leasing.getEnddate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        contents.add("Monthly Payment: " + leasing.getMonthlypayment());
        contents.add("Initial Value: " + leasing.getInitialValue());
        byte[] pdfContent = pdfService.createPdf(contents);
        sendEmailWithAttachment(iDemandey.getUser().getEmail(), "Leasing Contract", "Please find attached the leasing contract.", pdfContent);
        return createdLeasing;
    }

    private void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment) throws MessagingException, MessagingException {
        new Thread(() -> {
            try {
                final String username = "klaimohamed1994@gmail.com";
                final String password = "eblgesjukcqncydy";

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                Session session = Session.getInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                // Create email message
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);

                // Create message body
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);

                // Create attachment
                DataSource source = new ByteArrayDataSource(attachment, "application/pdf");
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName("LeasingContract.pdf");

                // Construct email content
                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(attachmentPart);
                message.setContent(multipart);

                // Send email
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @GetMapping("/{leaseid}")
    public ResponseEntity<Leasing> getLeasingById(@PathVariable("leaseid") Long leaseId) {
        Optional<Leasing> leasing = leasingService.getLeasingById(leaseId);
        if (leasing.isPresent()) {
            return ResponseEntity.ok(leasing.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{leaseid}")
    public ResponseEntity<Void> deleteLeasingById(@PathVariable("leaseid") Long leaseId) {
        Optional<Leasing> existingLeasing = leasingService.getLeasingById(leaseId);
        if (existingLeasing.isPresent()) {
            leasingService.deleteLeasingById(leaseId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/assignusertoleasing/{id}/{leaseid}")
    public String assignUserToLeasing(@PathVariable Long id, @PathVariable Long leaseid) {
        return leasingService.assignUserToLeasing(id , leaseid );
    }
    @GetMapping("/getbydemande")
    public Iterable<Leasing> leasingsbydemande (@RequestParam("id") Long id){
        Iterable<Leasing> leasings= leasingService.leasingsbydemande(id);

        return leasings;
    }
    @PutMapping("/update")
    public Leasing update(@RequestParam("id") Long id, @RequestBody Leasing leasing) {
        return leasingService.updateLeasing(  leasing,id );
    }
    @GetMapping("all")
    public Iterable<Leasing> getall() {
        return leasingService.leasingList( );
    }


    @GetMapping("/{leasingId}/interest-rate")
    public float getAnnualInterestRate(@PathVariable Long leasingId) {
        Leasing leasing = leasingService.getLeasingById(leasingId)
                .orElseThrow(() -> new NoSuchElementException("Leasing not found with id: " + leasingId));
        return leasingService.calculateAnnualInterestRate(leasing);
    }

    @GetMapping("/{leasingId}/late-payment-percentage")
    public float getLatePaymentPercentage(@PathVariable Long leasingId) {
        Optional<Leasing> leasing = leasingService.getLeasingById(leasingId);
        if (leasing.isPresent()) {
            return leasingService.calculateLatePaymentPercentage(leasing.get());
        } else {
            // Gérer le cas où le leasing n'existe pas
            throw new OpenApiResourceNotFoundException("Leasing not found with id: " + leasingId);
        }
    }

}