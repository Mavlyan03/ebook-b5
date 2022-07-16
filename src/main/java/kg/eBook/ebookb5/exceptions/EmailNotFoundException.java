package kg.eBook.ebookb5.exceptions;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
