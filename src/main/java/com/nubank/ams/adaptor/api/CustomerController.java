package com.nubank.ams.adaptor.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.nubank.ams.domain.entity.Customer;
import com.nubank.ams.domain.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	// Get all customers (Aggregate root)
	@GetMapping
	List<Customer> all() {
		// return customerService.getAllCustomers();
		List<Customer> cust = customerService.getAllCustomers();
		logger.debug("Get All customers - results >> {}", cust);

		return cust;
	}

	// Get customer by customer ID
	@GetMapping("/{id}")
	Customer customerById(@PathVariable Long id) {
		return customerService.getCustomerById(id);
	}

	// Create new customer
	@PostMapping
	Customer createCustomer(@RequestBody Customer newCustomer) {
		return customerService.createCustomer(newCustomer);
	}

	// Create new customer account
	/*
	 * @PostMapping("/{customerId}/accounts") Customer
	 * createCustomerAccount(@PathVariable Long customerId,@RequestBody Account
	 * newCustomerAcc) { return
	 * customerService.createCustomerAccount(customerId,newCustomerAcc); }
	 */

	// Get customers by account ID
	@GetMapping("/accounts/{id}")
	List<Customer> customersByAccountId(@PathVariable Long id) {
		return customerService.getCustomersByAccountId(id);
	}
}
