package com.nubank.ams.adaptor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nubank.ams.domain.entity.Customer;

 

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}