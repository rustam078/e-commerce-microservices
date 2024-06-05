package com.abc.ecommerce.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ecommerce.dto.ProductPurchaseRequest;
import com.abc.ecommerce.dto.ProductPurchaseResponse;
import com.abc.ecommerce.dto.ProductRequest;
import com.abc.ecommerce.dto.ProductResponse;
import com.abc.ecommerce.entity.Product;
import com.abc.ecommerce.exception.ProductNotFoundException;
import com.abc.ecommerce.exception.ProductPurchaseException;
import com.abc.ecommerce.mapper.ProductMapper;
import com.abc.ecommerce.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductMapper mapper;
	@Autowired
	private ProductRepo repo;

	public Integer createProduct(ProductRequest request) {
		Product product = mapper.convertToProduct(request);
		Product prod = repo.save(product);
		return prod.getId();
	}

	public ProductResponse findProductById(Integer id) {
		return repo.findById(id).map(mapper::convertToProductResponse)
				.orElseThrow(() -> new ProductNotFoundException("no customer found for this id " + id));
	}

	public List<ProductResponse> getAllProducts() {
		return repo.findAll().stream().map(mapper::convertToProductResponse).collect(Collectors.toList());
	}

	public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {

		List<Integer> productIds = request.stream().map(ProductPurchaseRequest::getProductId).toList();
		List<Product> storesProducts = repo.findAllByIdInOrderById(productIds);
		if (productIds.size() != storesProducts.size()) {
			throw new ProductPurchaseException("One or more products does not exists");
		}

		List<ProductPurchaseRequest> storeRequest = request.stream()
				.sorted(Comparator.comparing(ProductPurchaseRequest::getProductId)).toList();

		List<ProductPurchaseResponse> purchaseProducts = new ArrayList<>();

		for (int i = 0; i < storesProducts.size(); i++) {
			Product product = storesProducts.get(i);
			ProductPurchaseRequest productPurchaseRequest = storeRequest.get(i);
			if (product.getAvailableQuantity() < productPurchaseRequest.getQuantity()) {
				throw new ProductPurchaseException("Insufficent stock quantity found");
			}
			double newAvailableQuantity = product.getAvailableQuantity() - productPurchaseRequest.getQuantity();
			product.setAvailableQuantity(newAvailableQuantity);
			repo.save(product);
			purchaseProducts.add(mapper.toProductPurchaseResponse(product, productPurchaseRequest.getQuantity()));
		}
		return purchaseProducts;
	}

}
