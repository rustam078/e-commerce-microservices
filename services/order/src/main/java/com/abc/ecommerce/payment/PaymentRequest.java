package com.abc.ecommerce.payment;

import java.math.BigDecimal;

import com.abc.ecommerce.customer.CustomerResponse;
import com.abc.ecommerce.entity.PaymentMethod;

public record PaymentRequest(
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
) {
}