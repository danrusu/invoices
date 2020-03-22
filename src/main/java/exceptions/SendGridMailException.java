package exceptions;

public class SendGridMailException extends Error{
    public SendGridMailException(Throwable cause) {
        super(cause);
    }
}
