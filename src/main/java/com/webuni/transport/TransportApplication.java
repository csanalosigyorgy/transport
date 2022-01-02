package com.webuni.transport;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webuni.transport.model.TransportUser;
import com.webuni.transport.repository.TransportUserRepository;

@SpringBootApplication
public class TransportApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TransportApplication.class, args);
	}

	@Autowired
	private TransportUserRepository transportUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		transportUserRepository.addUser(new TransportUser("pap_bela", passwordEncoder.encode("pass"), Set.of("TransportManager") ));
		transportUserRepository.addUser(new TransportUser("kiss_jolan", passwordEncoder.encode("pass"), Set.of("AddressManager") ));
		transportUserRepository.addUser(new TransportUser("vak_istvan", passwordEncoder.encode("pass"), Set.of("TransportManager", "AddressManager") ));
	}
}
