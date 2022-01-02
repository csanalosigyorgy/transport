package com.webuni.transport.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webuni.transport.dto.AddressDto;
import com.webuni.transport.mapper.AddressMapper;
import com.webuni.transport.model.Address;
import com.webuni.transport.service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	private static final String TOTAL_RECORD = "X-Total-Count";

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private AddressService addressService;

	@PostMapping
	@PreAuthorize("hasAuthority('AddressManager')")
	public AddressDto createAddress(@Valid @RequestBody AddressDto addressDto) {
		return addressMapper.toAddressDto(addressService.saveAddress(addressDto));
	}

	@GetMapping
	public List<AddressDto> getAllAddresses() {
		return addressMapper.toAddressDtos(addressService.findAll());
	}

	@GetMapping("/{id}")
	public AddressDto getAddressById(@PathVariable Long id) {
		return addressMapper.toAddressDto(addressService.findById(id));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('AddressManager')")
	public void deleteAddressById(@PathVariable Long id) {
		addressService.deleteAddressById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('AddressManager')")
	public AddressDto modifyAddress(@PathVariable Long id, @Valid @RequestBody AddressDto addressDto){
		return addressMapper.toAddressDto(addressService.updateAddress(id, addressDto));
	}

	@GetMapping("/search")
	public List<AddressDto> searchAddress(@RequestBody AddressDto example, Pageable pageable, HttpServletResponse response){
		Page<Address> page = addressService.searchAddress(example, pageable);
		response.addHeader(TOTAL_RECORD, String.valueOf(page.getTotalElements()));
		return addressMapper.toAddressDtos(page.getContent());
	}
}
