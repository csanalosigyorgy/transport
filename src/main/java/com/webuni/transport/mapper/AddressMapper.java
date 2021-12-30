package com.webuni.transport.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.webuni.transport.dto.AddressDto;
import com.webuni.transport.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	AddressDto toAddressDto(Address address);

	List<AddressDto> toAddressDtos(List<Address> addresses);

	Address toAddress(AddressDto addressDto);
}
