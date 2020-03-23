package base;

import exceptions.GetMailMessageException;
import exceptions.SendMailException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getAnonymousLogger;
import static javax.mail.Message.RecipientType.TO;
import static utils.FileUtils.getFilePaths;

// Source: https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
// Gmail account setup - less secure application access ON
// https://myaccount.google.com/u/1/lesssecureapps?pageId=none
class GmailSender {
    private Logger logger;
    private String gmailUsername;
    private String gmailPassword;

    private GmailSender(String gmailUsername, String gmailPassword) {
        this.gmailUsername = gmailUsername;
        this.gmailPassword = gmailPassword;
        this.logger = getAnonymousLogger();
    }

    public static GmailSender getGmailSender(String gmailUsername, String gmailPassword){

        return new GmailSender(gmailUsername, gmailPassword);
    }

    public void sendMail(EmailObject email) {

        Session session = getSession(
                getAuthenticator(gmailUsername, gmailPassword),
                getEmailSslProperties());

        MimeMessage message = getMimeMessage(session, email);

        try {
            Transport.send(message);
            logger.log(INFO, "Email sent to " + email.getTo());
        }
        catch (MessagingException e){
            throw new SendMailException(e);
        }
    }

    private Session getSession(Authenticator authenticator, Properties properties) {
        return Session.getInstance(
                properties,
                authenticator
        );
    }

    private Authenticator getAuthenticator(String username, String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    private Properties getEmailSslProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return properties;
    }

    private MimeMessage getMimeMessage(Session session, EmailObject email) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email.getFrom()));
            message.setRecipients(TO, InternetAddress.parse(email.getTo()));
            message.setSubject(email.getSubject());

            // multipart message
            Multipart multipartMessage = new MimeMultipart();

            // set message text
            setMessageText(multipartMessage, email.getTextContent());

            // message attachments
            setMessageAttachments(multipartMessage, email.getAttachmentsFolder());

            message.setContent(multipartMessage);
            return message;

        } catch (MessagingException e) {
            throw new GetMailMessageException(e);
        }
    }

    private void setMessageText(Multipart multipartMessage, String text) throws MessagingException {
        BodyPart messageText = new MimeBodyPart();
        messageText.setText(text);
        multipartMessage.addBodyPart(messageText);
    }

    private void setMessageAttachments(Multipart multipartMessage, Path attachmentsFolder) {
        String downloadFolderMsg = "Attaching files from " + attachmentsFolder;
        logger.log(INFO, downloadFolderMsg);
        getFilePaths(attachmentsFolder).forEach(
                filePath -> {
                    DataSource source = new FileDataSource(filePath.toString());
                    MimeBodyPart fileAttachment = new MimeBodyPart();
                    try {
                        fileAttachment.setDataHandler(new DataHandler(source));
                        String filename = filePath.getFileName().toString();
                        fileAttachment.setFileName(filename);
                        multipartMessage.addBodyPart(fileAttachment);
                        String attachedMsg = "Attached " + filename;
                        logger.log(INFO, attachedMsg);
                    }
                    catch(MessagingException e){
                        logger.log(SEVERE, "e");
                    }
                }
        );
    }
}