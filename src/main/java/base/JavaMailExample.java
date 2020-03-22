package base;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import static javax.mail.Message.RecipientType.TO;

class JavaMailExample {
    public static void main(String[] args) {

        final String user = "danrusu@qatools.ro";
        final String password = "mailServerPassw0rd";

        String to = "qatools.ro@gmail.com";
        //String to = "danginkgo@yahoo.com";

        //1) get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "0.0.0.0");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        //2) compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(TO, new InternetAddress(to));
            message.setSubject("Message Alert");

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("This is message body");

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = "invoices.iml";
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            message.setContent(multipart);

            //7) send message
            Transport.send(message);

            System.out.println("Email sent to " + to);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}