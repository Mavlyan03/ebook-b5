package kg.eBook.ebookb5.exceptions;

public class InvalidPromocodeException extends RuntimeException{

    public InvalidPromocodeException() {
    }

    public InvalidPromocodeException(String message) {
        super(message);
    }
}
