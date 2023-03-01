package org.generation.app.security;

import org.generation.app.model.Customer;
import org.generation.app.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	ICustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerRepository.findOneByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));
		return new UserDetailsImpl(customer);
	}

}
