package com.abc.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import com.abc.ecommerce.entity.PaymentMethod;
import com.abc.ecommerce.product.PurchaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonInclude(Include.NON_EMPTY)
public record OrderRequest(
    Integer id,
    String reference,
    @NotNull(message = "Payment method should be precised")
    PaymentMethod paymentMethod,
    @NotNull(message = "Customer should be present")
    Long customerId,
    @NotEmpty(message = "You should at least purchase one product")
    List<PurchaseRequest> products
) {

}