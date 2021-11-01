package com.nubank.ams.domain.service.exception;

 
public class CustomerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 199991L;

	public CustomerNotFoundException(Long id) {
    super("Could not find customer " + id);
  }
}
