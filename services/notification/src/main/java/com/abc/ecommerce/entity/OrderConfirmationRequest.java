package com.abc.ecommerce.entity;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmationRequest(
	    String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
	   ) {

}
