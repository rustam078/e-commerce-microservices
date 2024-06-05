package com.abc.ecommerce.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductPurchaseResponse {

	private Integer productId;
	private String name;
	private String description;
	private BigDecimal price;
	private double quantity;
}
