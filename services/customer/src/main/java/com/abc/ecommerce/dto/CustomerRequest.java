package com.abc.ecommerce.dto;

import org.springframework.validation.annotation.Validated;

import com.abc.ecommerce.entity.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

   private String id;
   @NotNull(message="Customer firstname is required")
	private String firstname;
   @NotNull(message="Customer lastname is required")
	private String lastname;
   @NotNull(message="Customer email is required")
   @Email(message = "Customer email is not valid")
	private String email;
	private Address address;
}
