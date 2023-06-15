package com.pi.kursy.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String status;
    private final String message;

    public static ErrorResponse fromGenericException(GenericException ex) {
        return new ErrorResponse(ex.getStatus(), ex.getMessage());
    }
}
