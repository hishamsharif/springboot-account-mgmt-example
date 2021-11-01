package com.nubank.ams.adaptor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nubank.ams.domain.entity.Customer;

 

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	// Using the spring data jpa named entity graph as solution for the popular N+1 issue with hibernate 
	@EntityGraph(value = "customer.accounts", type = EntityGraphType.FETCH)
	List<Customer> findAll();

	List<Customer> findByAccountsId(Long id);
}