package com.webuni.transport.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
	private Double length;
	private Double width;
}
