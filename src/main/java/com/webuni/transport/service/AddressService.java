package com.webuni.transport.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.webuni.transport.dto.AddressDto;
import com.webuni.transport.dto.AddressSearchResultDto;
import com.webuni.transport.mapper.AddressMapper;
import com.webuni.transport.model.Address;
import com.webuni.transport.repository.AddressRepository;
import com.webuni.transport.service.base.BaseAddressService;
import com.webuni.transport.specification.AddressSpecification;

@Service
public class AddressService implements BaseAddressService {

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public AddressDto saveAddress(AddressDto addressDto){
		Address address = addressMapper.toAddress(addressDto);
		checkIfEntityNull(address);
		checkIfEntityIdNonNull(address.getId());
		return addressMapper.toAddressDto(addressRepository.save(address));
	}

	@Override
	public List<AddressDto> findAll(){
		return addressMapper.toAddressDtos(addressRepository.findAll());
	}

	@Override
	public AddressDto findById(Long id){
		return addressMapper.toAddressDto(addressRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("A keresett rekord nem létezik az adatbázisban (id: " + id + ")")));
	}

	@Override
	public void deleteAddressById(Long id){
		if(addressRepository.existsById(id))
			addressRepository.deleteById(id);
	}

	@Override
	@Transactional
	public AddressDto updateAddress(Long id, AddressDto addressDto){
		Address address = addressMapper.toAddress(addressDto);

		checkIfEntityNull(address);
		checkIfEntityIdNonNull(address.getId());

		if(!address.getId().equals(id))
			throw new IllegalArgumentException("A beküldött id-k nem egyeznek.");

		if(!addressRepository.existsById(id))
			throw new NoSuchElementException("A keresett rekord nem létezik az adatbázisban (id: " + id + ")");

		address.setId(id);
		addressRepository.save(address);
		return addressMapper.toAddressDto(address);
	}

	@Override
	public AddressSearchResultDto searchAddress(AddressDto exampleDto, Pageable pageable){
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

		Page<Address> addressesPage = addressRepository.findAll(spec, pageable);
		return new AddressSearchResultDto(addressMapper.toAddressDtos(addressesPage.getContent()), addressesPage.getTotalElements());
	}

	protected void checkIfEntityNull(Object entity){
		if(Objects.isNull(entity))
			throw new IllegalArgumentException("Nem küldött be objektumot.");
	}

	protected void checkIfEntityIdNonNull(Long id){
		if(Objects.nonNull(id))
			throw new IllegalArgumentException("Az objektum azonosítója már ki van töltve.");
	}
}
