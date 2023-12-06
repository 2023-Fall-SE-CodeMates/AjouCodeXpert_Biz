package codemates.ajoucodexpert.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private final int code;
    private final String message;

    public ErrorResponse(final ExceptionType errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getErrorMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
