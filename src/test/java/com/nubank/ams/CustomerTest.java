package com.nubank.ams;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nubank.ams.adaptor.api.CustomerController;
import com.nubank.ams.domain.entity.Customer;
import com.nubank.ams.domain.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	private static String apiEnpointBase = "/api/customers";

	private static Customer cust1 = Customer.builder().id(1L).name("Bob").email("bob@abc.com").build();
	private static Customer cust2 = Customer.builder().id(2L).name("John Doe").email("john@abc.com").build();

	@Test
	public void GivenGetAllCustomerShouldReturnListOfAllCustomers() throws Exception {

		List<Customer> customerList = new ArrayList<>(Arrays.asList(cust1, cust2));

		when(customerService.getAllCustomers()).thenReturn(customerList);
		this.mockMvc.perform(get(apiEnpointBase)).andDo(print()).andExpect(status().isOk())
				// .andExpect(content().string(containsString("Hello, Mock")))
				.andExpect(jsonPath("$[0].name", is("Bob"))).andExpect(jsonPath("$[0].email", is("bob@abc.com")));

	}

}
