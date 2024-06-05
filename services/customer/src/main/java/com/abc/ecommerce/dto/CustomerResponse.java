package com.abc.ecommerce.dto;

import com.abc.ecommerce.entity.Address;

import lombok.Data;

@Data
public class CustomerResponse {

	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private Address address;

}
