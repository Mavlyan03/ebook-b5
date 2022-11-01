package kg.eBook.ebookb5.exceptions;

public class InvalidPromoCodeException extends RuntimeException {

    public InvalidPromoCodeException() {
    }

    public InvalidPromoCodeException(String message) {
        super(message);
    }

}
