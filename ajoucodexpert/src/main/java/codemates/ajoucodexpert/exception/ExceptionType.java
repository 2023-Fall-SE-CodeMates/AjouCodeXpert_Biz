package codemates.ajoucodexpert.exception;

import lombok.AllArgsConstructor;

public enum ExceptionType {
    DATA_NOT_FOUND(100, "Data not found"),
    DATA_ALREADY_EXIST(101, "Data already exist"),
    INVALID_INPUT(200, "Invalid input"),
    UNAUTHORIZED(300, "Unauthorized");

    private final int errorCode;
    private final String errorMessage;

    ExceptionType(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
