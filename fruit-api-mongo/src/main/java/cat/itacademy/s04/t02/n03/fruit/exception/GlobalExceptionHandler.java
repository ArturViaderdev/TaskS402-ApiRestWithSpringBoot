package cat.itacademy.s04.t02.n03.fruit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class  GlobalExceptionHandler {
    @ExceptionHandler({NumberFormatException.class, MethodArgumentNotValidException.class,MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> badRequest(RuntimeException e, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST, "Valor no vàlid", path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({FruitsEmptyException.class, ClientNameIsEmptyException.class})
    public ResponseEntity<ErrorResponse> fruitsEmpty(RuntimeException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({OrderNotFoundException.class, OrderIdDoesNotExists.class})
    public ResponseEntity<ErrorResponse> notFoundOrder(RuntimeException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse body = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), path);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
