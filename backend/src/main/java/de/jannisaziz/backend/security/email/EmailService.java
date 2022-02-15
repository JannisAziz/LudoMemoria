package de.jannisaziz.backend.security.email;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    public String sendEmail(String recipientEmail, String recipientName, String token) throws IllegalStateException {

        String activationLink = "http://192.168.2.104:8080/api/auth/confirm=" + token;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("capstoneludomemoria", "ludo-memoria");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("capstoneludomemoria@gmail.com", "noreply-ludoMemoria"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Verify Email");
            message.setContent(buildVerifyEmailHTMLBodySimple(recipientName, activationLink), "text/html; charset=utf-8");

            Transport.send(message);
            return "Confirmation email sent";
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Exception in EmailService.java: " + e.getMessage());
        }
    }

    private String buildVerifyEmailHTMLBodySimple(String username, String activationLink) {
        return "<div>\n" +
                "   <p>Hi " + username + ", Thank you for registering. Please click on the below link to activate your account:</p>" +
                "   <a href=\"" + activationLink + "\">Activate Now</a>" +
                "   <p>See you soon</p>" +
                "</div>";
    }
}