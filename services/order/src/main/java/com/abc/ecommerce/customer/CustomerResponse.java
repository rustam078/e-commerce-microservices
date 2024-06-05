package com.abc.ecommerce.customer;

public record CustomerResponse(
	Long  id,
    String firstname,
    String lastname,
    String email
) {

}