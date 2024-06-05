package com.abc.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ecommerce.dto.CustomerRequest;
import com.abc.ecommerce.dto.CustomerResponse;
import com.abc.ecommerce.entity.Address;
import com.abc.ecommerce.entity.Customer;
import com.abc.ecommerce.exception.CustomerNotFoundException;
import com.abc.ecommerce.mapper.CustomerMapper;
import com.abc.ecommerce.repo.CustomerRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerMapper mapper;

	public List<CustomerResponse> getAllCustomers() {
		return customerRepository.findAll().stream().map(mapper::convertToCustomerResponse)
				.collect(Collectors.toList());
	}

	public CustomerResponse getCustomerById(Long id) {
		return customerRepository.findById(id).map(mapper::convertToCustomerResponse)
				.orElseThrow(() -> new CustomerNotFoundException("no customer found for this id " + id));
	}

	public Long createCustomer(CustomerRequest customer) {
		Customer customer2 = new Customer();
		BeanUtils.copyProperties(customer, customer2);
		return customerRepository.save(customer2).getId();
	}

	public Customer updateCustomer(Long id, CustomerRequest customerDetails) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

		if (StringUtils.isNotBlank(customerDetails.getFirstname())) {
			customer.setFirstname(customerDetails.getFirstname());
		}
		if (StringUtils.isNotBlank(customerDetails.getLastname())) {
			customer.setLastname(customerDetails.getLastname());
		}
		if (StringUtils.isNotBlank(customerDetails.getEmail())) {
			customer.setEmail(customerDetails.getEmail());
		}
		if (customerDetails.getAddress() != null) {
			Address address = customer.getAddress();
			if (StringUtils.isNotBlank(customerDetails.getAddress().getHouseNumber())) {
				address.setHouseNumber(customerDetails.getAddress().getHouseNumber());
			}
			if (StringUtils.isNotBlank(customerDetails.getAddress().getStreet())) {
				address.setStreet(customerDetails.getAddress().getStreet());
			}
			if (StringUtils.isNotBlank(customerDetails.getAddress().getZipCode())) {
				address.setZipCode(customerDetails.getAddress().getZipCode());
			}
	
			customer.setAddress(address);
		}

		return customerRepository.save(customer);
	}

	public void deleteCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
		customerRepository.delete(customer);
	}

	public Boolean existsCustomerById(Long id) {
		return customerRepository.existsById(id);
	}
}
