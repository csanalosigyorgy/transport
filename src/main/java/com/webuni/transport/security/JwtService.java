package com.webuni.transport.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {

	private static final String AUTH = "Auth";
	private static final Integer DURATION = 10;
	private static final String ISSUER = "TransportApp";
	private static final String SECRET = "mysecret";
	public static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

	public String createJwtToken(UserDetails principal) {
		return JWT.create()
				.withSubject(principal.getUsername())
				.withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
				.withExpiresAt(new Date((System.currentTimeMillis()) + TimeUnit.MINUTES.toMillis(DURATION)))
				.withIssuer(ISSUER)
				.sign(ALGORITHM);
	}

	public UserDetails decodeJwtToken(String jwtToken) {
		DecodedJWT decodedJwt = JWT.require(ALGORITHM)
				.withIssuer(ISSUER)
				.build()
				.verify(jwtToken);

		return new User(
				decodedJwt.getSubject(),
				"dummy",
				decodedJwt.getClaim(AUTH).asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	}
}
