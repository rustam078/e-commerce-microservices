package com.abc.ecommerce.dto;

import lombok.Data;

@Data
public class ProductPurchaseRequest {

	private Integer productId;
	private double quantity;
}
