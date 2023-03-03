package org.generation.app.service;

import java.util.List;

import org.generation.app.dto.CustomerDto;
import org.generation.app.model.Customer;

public interface ICustomerService {
	
	public List<CustomerDto> getAllCustomers();
	
	public List<Customer> getAllActiveCustomers();
	
	public CustomerDto getCustomerById(long idCustomer);
	
	public boolean existCustomerByEmail(String email);
	
	public Customer setCustomer(Customer customer);

}
