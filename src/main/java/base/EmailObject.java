package base;

import java.nio.file.Path;

public class EmailObject {
    private String from;
    private String to;
    private String subject;
    private String textContent;
    private Path attachmentsFolder;

    // change this to builder
    public EmailObject(String from,
                       String to,
                       String subject,
                       String textContent,
                       Path attachmentsFolder) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.textContent = textContent;
        this.attachmentsFolder = attachmentsFolder;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getTextContent() {
        return textContent;
    }

    public Path getAttachmentsFolder() {
        return attachmentsFolder;
    }
}
