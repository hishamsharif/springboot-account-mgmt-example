package com.test.accountsservice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubank.ams.adaptor.api.CustomerController;
import com.nubank.ams.adaptor.repository.AccountRepository;
import com.nubank.ams.adaptor.repository.CustomerRepository;
import com.nubank.ams.domain.entity.Account;
import com.nubank.ams.domain.entity.Customer;
import com.nubank.ams.domain.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerAccountIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	private CustomerRepository repository;

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private CustomerService customerService;

	private static String apiEnpointBase = "/api/customers";

	private static Customer cust1 = Customer.builder().id(1L).name("Bob").email("bob@abc.com").build();
	private static Customer cust2 = Customer.builder().id(2L).name("John Doe").email("john@abc.com").build();

	@Test
	public void givenCustomers_whenGetAllCustomers_thenStatus200() throws Exception {

		List<Customer> customers = new ArrayList<>(Arrays.asList(cust1, cust2));
		Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
		Mockito.when(repository.findAll()).thenReturn(customers);

		mockMvc.perform(MockMvcRequestBuilders.get(apiEnpointBase).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				// .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(jsonPath("$[0].name", is("Bob"))).andExpect(jsonPath("$[0].email", is("bob@abc.com")));

	}

	@Test
	public void givenCustomers_whenGetCustomerById_thenStatus200() throws Exception {

		Mockito.when(customerService.getCustomerById(cust1.getId())).thenReturn(cust1);
		Mockito.when(repository.findById(cust1.getId())).thenReturn(java.util.Optional.of(cust1));

		mockMvc.perform(MockMvcRequestBuilders.get(apiEnpointBase + "/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Bob")));
	}

	@Test
	public void createCustomer_thenStatusSuccess() throws Exception {
		Customer customer = Customer.builder().name("John Doe").email("john@abc.com").build();

		Mockito.when(customerService.createCustomer(customer)).thenReturn(customer);
		Mockito.when(repository.save(customer)).thenReturn(customer);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(apiEnpointBase)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(customer));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue())).andDo(print())
				.andExpect(jsonPath("$.name", is("John Doe")));
	}

	//@Test
	public void createCustomerAccount_thenStatusSuccess() throws Exception {
		Account account1 = Account.builder().id(2L).accNumber("020-023131-010").balance(2500).build();
		Customer customer1 = Customer.builder().id(1L).name("Bob").email("bob@abc.com")
				.accounts(new HashSet<>(Arrays.asList(account1))).build();
		// Customer customer2 = Customer.builder().name("John
		// Doe").email("john@abc.com").accounts(new
		// HashSet<>(Arrays.asList(account1))).build();

		Mockito.when(customerService.createCustomerAccount(customer1.getId(), account1)).thenReturn(customer1);
		Mockito.when(accountRepository.save(account1)).thenReturn(account1);
		Mockito.when(repository.save(customer1)).thenReturn(customer1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post(apiEnpointBase + "/" + customer1.getId() + "/accounts").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(account1));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				// .andExpect(jsonPath("$", hasSize(1)))
				.andDo(print()).andExpect(jsonPath("$.name", is("Bob"))).andExpect(jsonPath("$.accounts", hasSize(1)));
	}

}
