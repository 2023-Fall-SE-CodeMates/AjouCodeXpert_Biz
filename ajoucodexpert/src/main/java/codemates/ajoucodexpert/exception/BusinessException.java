package codemates.ajoucodexpert.exception;

public class BusinessException extends RuntimeException {
    private final int ERROR_CODE;

    public BusinessException(int errorCode, String message) {
        super(message);
        ERROR_CODE = errorCode;
    }

    public BusinessException(String message) {
        this(100, message); // default error code = data not found
    }

    public BusinessException(ExceptionType exceptionType, String message) {
        this(exceptionType.getErrorCode(), exceptionType.getErrorMessage());
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

    public String getErrorMessage() {
        return getMessage();
    }
}
