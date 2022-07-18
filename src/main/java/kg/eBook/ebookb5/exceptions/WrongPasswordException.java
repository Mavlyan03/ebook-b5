package kg.eBook.ebookb5.exceptions;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
