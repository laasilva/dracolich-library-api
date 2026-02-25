package dm.dracolich.library.web.controller;

import dm.dracolich.library.dto.DmdResponse;
import dm.dracolich.library.dto.exception.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<DmdResponse<?>> handleResponseException(ResponseException ex) {
        return new ResponseEntity<>(new DmdResponse<>(false, null, ex.getErrors(),
                ex.getHttpStatus(), ex.getMessage()), ex.getHttpStatus());
    }
}
