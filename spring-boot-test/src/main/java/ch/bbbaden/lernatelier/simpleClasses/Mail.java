package ch.bbbaden.lernatelier.simpleClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
    private static final String MAIL_ADDRESS_OF_SENDER= "quicktey@gmail.com";
    private static final String HOST = "smtp.gmail.com";
    private static final String MAIL_HEADER = "Danke für Ihren Einkauf bei codelineway";
    private static final String MAIL_PASSWORD = "cjshfshkpsbxkggj";

    public void sendMail(String mailAddressOfReciever, Order order, Cart cart, ArrayList<Attachment> attachments){

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
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(MAIL_ADDRESS_OF_SENDER));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAddressOfReciever));

            // Set Subject: header field
            message.setSubject(MAIL_HEADER);

            message.setDisposition(Part.INLINE);

            // Set the attachments
            MimeBodyPart attachmentPart = new MimeBodyPart();
            for (Attachment iterate: attachments){
                attachmentPart.attachFile(iterate.getFile());
            }

            // Set the BodyPart
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(cart.getMailText(order), "text/HTML; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
