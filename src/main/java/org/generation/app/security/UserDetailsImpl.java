package org.generation.app.security;

import java.util.Collection;
import java.util.Collections;

import org.generation.app.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	
	private final Customer customer;
	
	public UserDetailsImpl(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {

		return customer.getPassword();
	}

	@Override
	public String getUsername() {
 
		return customer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getNombre() {
		return customer.getFirstName();
	}

}
