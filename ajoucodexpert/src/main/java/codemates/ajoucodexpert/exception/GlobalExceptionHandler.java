package codemates.ajoucodexpert.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        final int errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode).body(new ErrorResponse(errorCode, e.getErrorMessage()));
    }
}
