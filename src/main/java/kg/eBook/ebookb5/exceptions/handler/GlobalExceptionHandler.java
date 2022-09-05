package kg.eBook.ebookb5.exceptions.handler;

import kg.eBook.ebookb5.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerInvalidDateException(InvalidDateException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(InvalidPromocodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerThisPromocodeIsInvalid(InvalidPromocodeException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFoundException(NotFoundException e) {

        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ExceptionResponse handlerAlreadyExistException(AlreadyExistException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(WrongEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerWrongEmailException(WrongEmailException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerWrongPasswordException(WrongPasswordException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerIllegalStateException(IllegalStateException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerUsernameNotFoundException(UsernameNotFoundException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
}
