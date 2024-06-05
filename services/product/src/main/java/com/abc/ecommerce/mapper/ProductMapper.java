package com.abc.ecommerce.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abc.ecommerce.dto.CategoryRequest;
import com.abc.ecommerce.dto.ProductPurchaseRequest;
import com.abc.ecommerce.dto.ProductPurchaseResponse;
import com.abc.ecommerce.dto.ProductRequest;
import com.abc.ecommerce.dto.ProductResponse;
import com.abc.ecommerce.entity.Category;
import com.abc.ecommerce.entity.Product;

@Component
public class ProductMapper {
	
	
	public Product convertToProduct(ProductRequest request) {
		Product product = new Product();
		Category category = new Category();	
		category.setId(request.getCategoryId());
		product.setName(request.getName());
		product.setAvailableQuantity(request.getAvailableQuantity());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setCategory(category);
		return product;
	}

	
	 public ProductResponse convertToProductResponse(Product product) {
		 ProductResponse productResponse = new ProductResponse();
		 CategoryRequest categoryRequest = new CategoryRequest();
	     BeanUtils.copyProperties(product.getCategory(), categoryRequest);
	     BeanUtils.copyProperties(product, productResponse);
	     productResponse.setCategory(categoryRequest);
	     return productResponse;
	    }


	public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
          ProductPurchaseResponse productPurchaseResponse = new ProductPurchaseResponse();
          BeanUtils.copyProperties(product, productPurchaseResponse);
          productPurchaseResponse.setProductId(product.getId());
          productPurchaseResponse.setQuantity(quantity);
		return productPurchaseResponse;
	}
}
