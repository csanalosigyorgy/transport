package com.webuni.transport.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportUser {

	private String username;
	private String password;
	private Set<String> roles;
}
