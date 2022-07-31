package kg.eBook.ebookb5.exceptions;

public class ThisPromocodeIsInvalid extends RuntimeException{

    public ThisPromocodeIsInvalid() {
    }

    public ThisPromocodeIsInvalid(String message) {
        super(message);
    }
}
