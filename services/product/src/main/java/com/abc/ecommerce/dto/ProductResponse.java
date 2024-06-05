package com.abc.ecommerce.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class ProductResponse {

	private String name;
	private String description;
	private double  availableQuantity;
	private BigDecimal price;

	private CategoryRequest category;
	
}
