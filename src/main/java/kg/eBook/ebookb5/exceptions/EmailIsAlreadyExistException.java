package kg.eBook.ebookb5.exceptions;

public class EmailIsAlreadyExistException extends RuntimeException{

    public EmailIsAlreadyExistException() {
    }

    public EmailIsAlreadyExistException(String message) {
        super(message);
    }
}
