package com.webuni.transport.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String countryCode;

	private String city;

	private String street;

	private String postcode;

	private String streetNumber;

	private double length;

	private double width;

}
