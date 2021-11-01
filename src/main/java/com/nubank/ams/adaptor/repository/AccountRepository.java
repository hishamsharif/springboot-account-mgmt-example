package com.nubank.ams.adaptor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nubank.ams.domain.entity.Account;
 

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByAccNumber(String accNumber);

}
