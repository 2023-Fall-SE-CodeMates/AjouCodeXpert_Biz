package codemates.ajoucodexpert.exception;

public class BusinessException extends RuntimeException {
    private final int ERROR_CODE;
    private final String ERROR_DETAIL;

    public BusinessException(int errorCode, String message) {
        super(message);
        ERROR_CODE = errorCode;
        ERROR_DETAIL = "";
    }

    public BusinessException(int errorCode, String message, String detail) {
        super(message);
        ERROR_CODE = errorCode;
        ERROR_DETAIL = detail;
    }

    public BusinessException(String message) {
        this(100, message); // default error code = data not found
    }

    public BusinessException(ExceptionType exceptionType, String detail) {
        this(exceptionType.getErrorCode(), exceptionType.getErrorMessage(), detail);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
    public String getErrorDetail() {
        return ERROR_DETAIL;
    }

    public String getErrorMessage() {
        return getMessage();
    }
}
