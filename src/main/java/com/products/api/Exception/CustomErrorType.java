package com.products.api.Exception;

import com.products.api.dto.ProductsDTO;

public class CustomErrorType extends ProductsDTO {
    private String errorMessage;

    public CustomErrorType(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
