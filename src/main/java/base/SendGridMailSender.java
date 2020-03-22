package base;

// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java

import exceptions.SendGridMailAttachmentsException;
import exceptions.SendGridMailException;
import com.sendgrid.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getAnonymousLogger;

import static java.nio.file.Files.newInputStream;
import static java.util.stream.Collectors.toList;

public class SendGridMailSender {

    private static List<Path> getFilePaths(Path folder, String extension) throws IOException {
        try (Stream<Path> paths = Files.walk(folder)) {
            return paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(extension))
                    .collect(toList());
        }
    }

    private static Function<Path, Attachments> filePathToAttachments = filePath -> {
        try {
            InputStream fileInputStream = newInputStream(filePath);
            String fileName = filePath.getFileName().toString();
            return new Attachments.Builder(fileName, fileInputStream).build();
        } catch (Exception e) {
            throw new SendGridMailAttachmentsException(e);
        }
    };

    public static void sendMail(EmailObject email) {

        Email emailFrom = new Email(email.getFrom());
        Email emailTo = new Email(email.getTo());
        Content content = new Content("text/plain", email.getTextContent());

        Mail mail = new Mail(emailFrom, email.getSubject(), emailTo, content);

        try {

            mail.attachments = getFilePaths(email.getAttachmentsFolder(), email.getAttachmentsExtension())
                    .stream()
                    .peek(filePath -> getAnonymousLogger().log(INFO, "Attaching " + filePath))
                    .map(filePathToAttachments)
                    .collect(toList());

            // add this to cli: -DSEND_GRID_API_KEY="correct API key"
            SendGrid sendGridClient = new SendGrid(System.getProperty("SEND_GRID_API_KEY"));
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            String body = mail.build();
            System.out.println(body);
            request.setBody(body);

            Response response = sendGridClient.api(request);
            Logger logger = getAnonymousLogger();
            logger.log(INFO, "Status code: " + response.getStatusCode());
            logger.log(INFO, "Body: " + response.getBody());
            logger.log(INFO, "Headers: " + response.getHeaders());

            logger.log(INFO, "Invoices mailed to: " + email.getTo());
        } catch (IOException e) {
            throw new SendGridMailException(e);
        }
    }
}