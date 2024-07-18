package com.learning.fms.exception.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorField {
    private String code;
    private String message;
    private String fieldName;
}
