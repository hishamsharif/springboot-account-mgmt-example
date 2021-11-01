package com.nubank.ams.domain.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nubank.ams.adaptor.repository.AccountRepository;
import com.nubank.ams.adaptor.repository.CustomerRepository;
import com.nubank.ams.domain.entity.Account;
import com.nubank.ams.domain.entity.Customer;
import com.nubank.ams.domain.service.exception.CustomerNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final CustomerRepository repository;
	private final AccountRepository accountRepository;

	public CustomerServiceImpl(CustomerRepository repository, AccountRepository accountRepository) {
		this.repository = repository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Customer createCustomer(Customer newCustomerAcc) {

		logger.debug("Creating Customer ", newCustomerAcc);
		return repository.save(newCustomerAcc);
	}

	@Override
	public Customer createCustomerAccount(Long customerId, Account newCustomerAcc) {

		Customer customer = getCustomerById(customerId);
		newCustomerAcc.addCustomer(customer);
		Account account = accountRepository.save(newCustomerAcc);
		customer.addAccount(account);

		logger.debug("Creating Customer account ", customer);

		// Optional<Customer> optionalCustomer = repository.findById(customerId); if
		// (!optionalCustomer.isPresent()) { ---- }

		return createCustomer(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {

		List<Customer> cust = repository.findAll();

		return cust;
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		return repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
	}

	@Override
	public List<Customer> getCustomersByAccountId(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * private void mapExistingAccount(Customer customer) { if (null !=
	 * customer.getAccounts()) {
	 * 
	 * customer.getAccounts().stream().forEach(acc -> {
	 * 
	 * Account account = accountRepository.findByAccNumber(acc.getAccNumber()); if
	 * (null == account) { account = new Account(); account.setCustomers(new
	 * HashSet<>()); }
	 * 
	 * customer.addAccount(account);
	 * 
	 * account.getCustomers().add(customer);
	 * 
	 * });
	 * 
	 * }
	 * 
	 * }
	 */

}
