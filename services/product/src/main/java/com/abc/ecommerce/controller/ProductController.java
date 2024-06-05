package com.abc.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.ecommerce.dto.ProductPurchaseRequest;
import com.abc.ecommerce.dto.ProductPurchaseResponse;
import com.abc.ecommerce.dto.ProductRequest;
import com.abc.ecommerce.dto.ProductResponse;
import com.abc.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	@Autowired
	private ProductService produtService;
	
	@PostMapping
	public ResponseEntity<Integer> createProduct(@RequestBody ProductRequest request){
		return ResponseEntity.ok(produtService.createProduct(request));
	}
	
	 @GetMapping
	    public List<ProductResponse> findAllProductss() {
	        return produtService.getAllProducts();
	    }
	
	  @GetMapping("/{id}")
	    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
	    	return ResponseEntity.ok(produtService.findProductById(id));
	    }
	  
	  @PostMapping("/purchase")
	  public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(@RequestBody List<ProductPurchaseRequest> request){
		  return ResponseEntity.ok(produtService.purchaseProduct(request));
	  }
}
