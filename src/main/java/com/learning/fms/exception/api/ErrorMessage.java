package com.learning.fms.exception.api;


public class ErrorMessage {

    private ErrorMessage() {
    }

    public static ErrorResponse errorResponse(String error, String dynamicValue) {
        return ErrorResponse.builder()
                .status(StatusType.ERROR)
                .error(Error.builder()
                        .code(error)
                        .message(dynamicValue)
                        .build())
                .build();
    }
}
