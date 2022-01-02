package com.webuni.transport.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.webuni.transport.model.TransportUser;

@Component
public class TransportUserRepository {

	private static final List<TransportUser> users = new ArrayList<>();

	public void addUser(TransportUser user) {
		users.add(user);
	}

	public Optional<TransportUser> findUserByUsername(String username) {
		return users.stream()
				.filter(u -> u.getUsername().equals(username))
				.findAny();
	}
}
