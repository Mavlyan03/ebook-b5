package kg.eBook.ebookb5.exceptions;

public class WrongEmailException extends RuntimeException {

    public WrongEmailException() {
    }

    public WrongEmailException(String message) {
        super(message);
    }

}
