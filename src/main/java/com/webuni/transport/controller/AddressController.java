package com.webuni.transport.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webuni.transport.dto.AddressDto;
import com.webuni.transport.dto.AddressSearchResultDto;
import com.webuni.transport.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	private static final String TOTAL_RECORD = "X-Total-Count";

	@Autowired
	private AddressService addressService;


	@PostMapping
	public AddressDto createAddress(@Valid @RequestBody AddressDto addressDto) {
		return addressService.saveAddress(addressDto);
	}

	@GetMapping
	public List<AddressDto> getAllAddresses() {
		return addressService.findAll();
	}

	@GetMapping("/{id}")
	public AddressDto getAddressById(@PathVariable Long id) {
		return addressService.findById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteAddressById(@PathVariable Long id) {
		addressService.deleteAddressById(id);
	}

	@PutMapping("/{id}")
	public AddressDto modifyAddress(@PathVariable Long id, @Valid @RequestBody AddressDto addressDto){
		return addressService.updateAddress(id, addressDto);
	}

	@GetMapping("/search")
	public List<AddressDto> searchAddress(@RequestBody AddressDto example, Pageable pageable, HttpServletResponse response){
		AddressSearchResultDto resultDto = addressService.searchAddress(example, pageable);
		response.addHeader(TOTAL_RECORD, String.valueOf(resultDto.getTotalCount()));
		return resultDto.getAddresses();
	}
}
