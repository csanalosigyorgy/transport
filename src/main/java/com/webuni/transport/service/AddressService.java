package com.webuni.transport.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.webuni.transport.dto.AddressDto;
import com.webuni.transport.mapper.AddressMapper;
import com.webuni.transport.model.Address;
import com.webuni.transport.repository.AddressRepository;
import com.webuni.transport.specification.AddressSpecification;

@Service
public class AddressService {

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private AddressRepository addressRepository;

	public Address saveAddress(AddressDto addressDto){
		Address address = addressMapper.toAddress(addressDto);
		checkIfEntityNull(address);
		checkIfEntityIdNonNull(address.getId());
		return addressRepository.save(address);
	}

	public List<Address> findAll(){
		return addressRepository.findAll();
	}

	public Address findById(Long id){
		return addressRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("This address does not exist."));
	}

	public void deleteAddressById(Long id){
		if(addressRepository.existsById(id))
			addressRepository.deleteById(id);
	}

	@Transactional
	public Address updateAddress(Long id, AddressDto addressDto){
		Address address = addressMapper.toAddress(addressDto);

		checkIfEntityNull(address);

		if(!address.getId().equals(id))
			throw new IllegalArgumentException("Posted ids are not equal.");

		if(!addressRepository.existsById(id))
			throw new NoSuchElementException("This address does not exist.");

		address.setId(id);
		addressRepository.save(address);
		return address;
	}

	public Page<Address> searchAddress(AddressDto exampleDto, Pageable pageable){
		Address example = addressMapper.toAddress(exampleDto);
		checkIfEntityNull(example);

		String countryCode = example.getCountryCode();
		String city = example.getCity();
		String street = example.getStreet();
		String postcode = example.getPostcode();

		Specification<Address> spec = Specification.where(null);

		if(StringUtils.hasText(countryCode))
			spec = spec.and(AddressSpecification.hasCountryCode(countryCode));

		if(StringUtils.hasText(city))
			spec = spec.and(AddressSpecification.hasCity(city));

		if(StringUtils.hasText(street))
			spec = spec.and(AddressSpecification.hasStreet(street));

		if(Objects.nonNull(postcode))
			spec = spec.and(AddressSpecification.hasPostcode(postcode));

		return addressRepository.findAll(spec, pageable);
	}

	protected void checkIfEntityNull(Object entity){
		if(Objects.isNull(entity))
			throw new IllegalArgumentException("Address was not posted.");
	}

	protected void checkIfEntityIdNonNull(Long id){
		if(Objects.nonNull(id))
			throw new IllegalArgumentException("Id has to be blank.");
	}
}
