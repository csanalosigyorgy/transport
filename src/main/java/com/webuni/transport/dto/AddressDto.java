package com.webuni.transport.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddressDto {

	private Long id;

	@Size(min = 2, max = 2)
	private String countryCode;

	@NotEmpty
	private String city;

	@NotEmpty
	private String street;

	@NotEmpty
	private String postcode;

	@NotEmpty
	private String streetNumber;

	private double length;

	private double width;
}
