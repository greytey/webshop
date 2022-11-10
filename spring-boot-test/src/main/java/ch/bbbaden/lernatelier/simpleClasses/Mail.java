package ch.bbbaden.lernatelier.simpleClasses;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
    private static final String MAIL_ADDRESS_OF_SENDER= "quicktey@gmail.com";
    private static final String HOST = "smtp.gmail.com";
    private static final String MAIL_HEADER = "Danke für Ihren Einkauf bei codelineway";
    private static final String MAIL_PASSWORD = "pprkniomgstudrul";

    public void sendMail(String mailAddressOfReciever, Order order, Cart cart){

        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(MAIL_ADDRESS_OF_SENDER, MAIL_PASSWORD);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(MAIL_ADDRESS_OF_SENDER));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAddressOfReciever));

            // Set Subject: header field
            message.setSubject(MAIL_HEADER);

            message.setDisposition(Part.INLINE);

            // Now set the actual message
            message.setContent(cart.getMailText(order), "text/html; charset=utf-8");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
