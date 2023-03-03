package org.generation.app.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.generation.app.dto.CustomerDto;
import org.generation.app.model.Customer;
import org.generation.app.repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CustomerDto> getAllCustomers() {
		List<Customer> allCustomers = (List<Customer>) customerRepository.findAll();
		
		List<CustomerDto> customersDto = allCustomers.stream()
					.map((customer) -> modelMapper.map(customer, CustomerDto.class))
					.collect(Collectors.toList());
		
		return customersDto  ;
	}

	@Override
	public List<Customer> getAllActiveCustomers() {
		List<Customer> allCustomers = customerRepository.findAllByActive(true);
		return allCustomers  ;
	}

	@Override
	public CustomerDto getCustomerById(long idCustomer) {		
		 Customer customer = customerRepository.findById(idCustomer)
				.orElseThrow( ()-> 
				new IllegalStateException("User does not exist with id: " + idCustomer));
		 CustomerDto customerDto =  modelMapper.map(customer, CustomerDto.class);
		 return customerDto;
	}

	@Override
	public Customer setCustomer(Customer customer) {
		
		if ( existCustomerByEmail(customer.getEmail() ) )
			throw new IllegalStateException("The user already exists with email: " + customer.getEmail());
		else if ( customer.getEmail().length() > Customer.FIELD_MAX_LENGTH )
			throw new IllegalStateException("Email length is greater than: " + Customer.FIELD_MAX_LENGTH);

		Customer newCustomer = customer;
		newCustomer.setActive(true);
		
		return customerRepository.save(newCustomer);
	}

	@Override
	public boolean existCustomerByEmail(String email) {
		return customerRepository.existsByEmail(email);
	}

}
