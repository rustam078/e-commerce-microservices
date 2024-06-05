package com.abc.ecommerce.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.abc.ecommerce.customer.CustomerResponse;
import com.abc.ecommerce.entity.PaymentMethod;
import com.abc.ecommerce.product.PurchaseResponse;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}