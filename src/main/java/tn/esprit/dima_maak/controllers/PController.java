package tn.esprit.dima_maak.controllers;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.ChargeCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.ChargeRequest;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.ChargeRepo;
import tn.esprit.dima_maak.repositories.UserRepository;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PController {

    static {
        Stripe.apiKey = "sk_test_51MxJRoJyDi4kbDaxdB6yovp4TTSTWsvNCP8NUEhHgf4WgyMGqvqjgKjNYWJVwBAQImPLLgh7sApos86UhbcZ6Ms900xCp9eAX7";
    }


    @Autowired
    ChargeRepo chargeRepo;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/charge")
    public String charge(@RequestBody ChargeRequest chargeRequest, @RequestParam("iduser") Long iduser) throws StripeException {
        User user = userRepository.findById(iduser).orElse(null);


        if (!user.getEmail().equals(chargeRequest.getEmail())) {
            return "Email user not exist";
        }

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", chargeRequest.getCardNumber());
        cardParams.put("exp_month", chargeRequest.getExp_month());
        cardParams.put("exp_year", chargeRequest.getExp_year());
        cardParams.put("cvc", chargeRequest.getCvv());
        cardParams.put("name", chargeRequest.getName());
        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);

        ChargeCreateParams params = new ChargeCreateParams.Builder()
                .setAmount(chargeRequest.getAmount())
                .setCurrency(chargeRequest.getCurrency())
                .setReceiptEmail(chargeRequest.getEmail())
                .setSource(token.getId())
                .build();

        Charge charge = Charge.create(params);
        if (charge.getStatus().equals("succeeded")) {

            chargeRepo.save(chargeRequest);
            sendemail(chargeRequest.getEmail(), user.getName());

        }
        return charge.toJson();

    }

    public void sendemail(String email,String nom) {
        new Thread(() -> {

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

                try {
                    Message msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(username));
                    msg.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(email));
                    msg.setSubject("Payment Valide");
                    String htmlContent = "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "<meta charset=\"utf-8\">\n" +
                            "\t<meta name=\"viewport\" content=\"width=device-width\">\n" +
                            "\t<title>Payment Receipt</title>\n" +
                            "\t<style>\n" +
                            "\t\t/* Main style */\n" +
                            "\t\t.wrapper {\n" +
                            "\t\t\tbackground-color: #f9f9f9;\n" +
                            "\t\t\tpadding: 20px;\n" +
                            "\t\t\tfont-family: Arial, sans-serif;\n" +
                            "\t\t\tfont-size: 16px;\n" +
                            "\t\t\tline-height: 1.5;\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\t.container {\n" +
                            "\t\t\tmax-width: 600px;\n" +
                            "\t\t\tmargin: 0 auto;\n" +
                            "\t\t\tbackground-color: #fff;\n" +
                            "\t\t\tpadding: 20px;\n" +
                            "\t\t\tborder-radius: 4px;\n" +
                            "\t\t\tbox-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\th1 {\n" +
                            "\t\t\tfont-size: 28px;\n" +
                            "\t\t\tmargin-bottom: 20px;\n" +
                            "\t\t\tcolor: #333;\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\ttable {\n" +
                            "\t\t\twidth: 100%;\n" +
                            "\t\t\tborder-collapse: collapse;\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\tth {\n" +
                            "\t\t\ttext-align: left;\n" +
                            "\t\t\tpadding: 10px;\n" +
                            "\t\t\tbackground-color: #f5f5f5;\n" +
                            "\t\t\tcolor: #333;\n" +
                            "\t\t\tfont-weight: bold;\n" +
                            "\t\t\tborder: 1px solid #ddd;\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\ttd {\n" +
                            "\t\t\tpadding: 10px;\n" +
                            "\t\t\tborder: 1px solid #ddd;\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\t/* Footer */\n" +
                            "\t\t.footer {\n" +
                            "\t\t\tmargin-top: 20px;\n" +
                            "\t\t\ttext-align: center;\n" +
                            "\t\t\tcolor: #999;\n" +
                            "\t\t\tfont-size: 12px;\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\t.footer a {\n" +
                            "\t\t\tcolor: #999;\n" +
                            "\t\t\ttext-decoration: none;\n" +
                            "\t\t}\n" +
                            "\n" +
                            "\t\t.footer a:hover {\n" +
                            "\t\t\tcolor: #333;\n" +
                            "\t\t}\n" +
                            "\t</style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "\t<div class=\"wrapper\">\n" +
                            "\t\t<div class=\"container\">\n" +
                            "\t\t\t<h1>Payment Receipt</h1>\n" +
                            "\t\t\t<p>Dear Mr/s " + nom + "Thank you for your payment. Below is your receipt:</p>\n" +
//                        "\t\t\t\t\t\t<strong>Total :</strong>\n" +
//                        "\t\t\t\t\t\t<strong>"+user.get+"TN" +
//                        "</ strong>\n" +
                            "\t\t\t<p>Please keep this receipt for your records. If you have any questions about your payment, please contact us at <a href=\"mailto:klaimohamed1994@gmail.com\">klaimohamed1994@gmail.com</a>.</p>\n" +
                            "\t\t</div>\n" +
                            "\t\t<div class=\"footer\">\n" +
                            "\t\t\t<p>Copyright &copy; 2024 Example Corp.\n" +
                            "\t\t\t\t<br>All rights reserved.</p>\n" +
                            "\t\t</div>\n" +
                            "\t</div>\n" +
                            "</body>\n" +
                            "</html>\n";

                    // yesnee3 body men html
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(htmlContent, "text/html");

                    // yesna3 multipart message w yezidd  message body lih
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    // yezi multipart message lil email
                    msg.setContent(multipart);

                    // yeb3idh mail
                    Transport.send(msg);
//

                    System.out.println("Email sent successfully!");


            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();

    }
}