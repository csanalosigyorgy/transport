package com.webuni.transport.service.base;

import java.util.List;
import org.springframework.data.domain.Pageable;

import com.webuni.transport.dto.AddressDto;
import com.webuni.transport.dto.AddressSearchResultDto;

public interface BaseAddressService {

	AddressDto saveAddress(AddressDto addressDto);

	List<AddressDto> findAll();

	AddressDto findById(Long id);

	void deleteAddressById(Long id);

	AddressDto updateAddress(Long id, AddressDto addressDto);

	AddressSearchResultDto searchAddress(AddressDto exampleDto, Pageable pageable);
}
