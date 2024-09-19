package me.paulojr.cadastrosfront.exceptions;

import me.paulojr.cadastrosfront.models.ErrorResponse;

public class ApiErrorException extends RuntimeException {

    private ErrorResponse errorResponse;

    public ApiErrorException(ErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

}
