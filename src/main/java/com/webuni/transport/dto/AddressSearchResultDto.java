package com.webuni.transport.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressSearchResultDto {

	List<AddressDto> addresses;
	long totalCount;
}
