package com.webuni.transport.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webuni.transport.model.TransportUser;
import com.webuni.transport.repository.TransportUserRepository;

@Service
public class TransportUserDetailsService implements UserDetailsService {

	@Autowired
	private TransportUserRepository transportUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TransportUser transportUser = transportUserRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));

		return new User(
				transportUser.getUsername(),
				transportUser.getPassword(),
				transportUser.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	}
}
