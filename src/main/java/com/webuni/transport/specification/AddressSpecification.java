package com.webuni.transport.specification;

import org.springframework.data.jpa.domain.Specification;

import com.webuni.transport.model.Address;
import com.webuni.transport.model.Address_;

public class AddressSpecification {

	public static Specification<Address> hasCountryCode(String countryCode) {
		return (root, cq, cb) -> cb.equal(root.get(Address_.countryCode), countryCode);
	}

	public static Specification<Address> hasCity(String city) {
		return (root, cq, cb) -> cb.like(root.get(Address_.city), city + "%");
	}

	public static Specification<Address> hasStreet(String street) {
		return (root, cq, cb) -> cb.like(root.get(Address_.street), street + "%");
	}

	public static Specification<Address> hasPostcode(String postcode) {
		return (root, cq, cb) -> cb.equal(root.get(Address_.postcode), postcode);
	}
}
