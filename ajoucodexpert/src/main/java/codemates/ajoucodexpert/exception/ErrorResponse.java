package codemates.ajoucodexpert.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private final int code;
    private final String message;
    private final String detail;

    public ErrorResponse(final ExceptionType errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getErrorMessage();
        this.detail = "";
    }

    public ErrorResponse(final ExceptionType errorCode, final String detail) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getErrorMessage();
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    public String getDetail() {
        return detail;
    }
}
