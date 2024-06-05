package com.abc.ecommerce.mapper;

import org.springframework.stereotype.Component;

import com.abc.ecommerce.dto.CustomerResponse;
import com.abc.ecommerce.entity.Customer;

@Component
public class CustomerMapper {

	
	 public CustomerResponse convertToCustomerResponse(Customer customer) {
	        CustomerResponse customerResponse = new CustomerResponse();
	        customerResponse.setId(customer.getId());
	        customerResponse.setFirstname(customer.getFirstname());
	        customerResponse.setLastname(customer.getLastname());
	        customerResponse.setEmail(customer.getEmail());
	        customerResponse.setAddress(customer.getAddress());
	        return customerResponse;
	    }
}
