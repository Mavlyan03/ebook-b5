package kg.eBook.ebookb5.exceptions.handler;

import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.ExceptionResponse;
import kg.eBook.ebookb5.exceptions.InvalidDateException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerNotFoundException(InvalidDateException e) {
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerBookFoundException(NotFoundException e) {

        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ExceptionResponse handlerNotFoundException(AlreadyExistException e) {
        return new ExceptionResponse(
                HttpStatus.FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
}
