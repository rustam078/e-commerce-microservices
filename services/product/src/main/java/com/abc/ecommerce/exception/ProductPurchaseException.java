package com.abc.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductPurchaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductPurchaseException(String message) {
        super(message);
    }
}
