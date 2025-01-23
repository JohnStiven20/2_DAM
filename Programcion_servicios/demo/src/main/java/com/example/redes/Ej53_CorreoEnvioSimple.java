package psp.tema4.ejemplos;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Ej53_CorreoEnvioSimple {
    public static void main(String[] args) {
        
        final Properties prop = new Properties();
        prop.put("mail.smtp.username", "stivensolanomacas@gmail.com");
        prop.put("mail.smtp.password", "cvkl dack mimu flbr");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        Session mailSession = Session.getInstance(prop, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.smtp.username"),
                        prop.getProperty("mail.smtp.password"));
            }
        });

        Message message = new MimeMessage(mailSession);

        try {
            message.setFrom(new InternetAddress("stivensolanomacas@gmail.com"));
            message.setSubject("Mi primer correo con Jakarta ");

            InternetAddress[] toEmailAddresses = InternetAddress.parse("stivensolanomacas@gmail.com");

            message.setRecipients(Message.RecipientType.TO, toEmailAddresses);
  
            message.setText("¡Hola! Este correo ha sido enviado usando Jakarta Mail");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static void mensajeSinFormato() {

        

    }
}
