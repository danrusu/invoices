package exceptions;

public class SendMailException extends Error{
    public SendMailException(Throwable cause) {
        super(cause);
    }
}
