package com.abc.ecommerce.exception;

import java.util.Map;

public record ErrorResponse(
    Map<String, String> errors
) {

}