package com.nubank.ams.domain.service;

import java.util.List;

import com.nubank.ams.domain.entity.Account;
import com.nubank.ams.domain.entity.Customer;

public interface CustomerService {

	public Customer createCustomer(Customer customer);

	public Customer createCustomerAccount(Long customerId, Account acc);

	public List<Customer> getAllCustomers();

	public Customer getCustomerById(Long customerId);

	public List<Customer> getCustomersByAccountId(Long accountId);

}
