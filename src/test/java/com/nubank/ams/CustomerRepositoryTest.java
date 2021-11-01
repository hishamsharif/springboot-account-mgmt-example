package com.nubank.ams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.nubank.ams.adaptor.repository.CustomerRepository;
import com.nubank.ams.domain.entity.Account;
import com.nubank.ams.domain.entity.Customer;

@DataJpaTest
@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=validate" })
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void injectedComponentsAreNotNull() {

		assertThat(customerRepository).isNotNull();
	}

	@Test
	public void whenSavedCustomerWithAccount_thenFindsByAccountId() throws Exception {

		Account account1 = Account.builder().accNumber("020-023131-010").balance(2500).build();
		Customer customer = Customer.builder().name("Bob").email("bob@abc.com")
				.accounts(new HashSet<>(Arrays.asList(account1))).build();

		Customer customerExpected = customerRepository.save(customer);
		Long accountId = customerExpected.getAccounts().stream().findFirst().get().getId();
		System.out.println(" accountId >> " + accountId);

		List<Customer> customerActual = customerRepository.findByAccountsId(accountId);

		assertThat(customerActual).isNotNull();

	}
}
