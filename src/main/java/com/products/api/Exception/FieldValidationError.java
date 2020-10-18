package com.products.api.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldValidationError {
    private String field;
    private String message;
    private MessageType type;
}
