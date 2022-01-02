package com.webuni.transport.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDto {

	private Long id;
	private AddressDto address;
	private LocalDateTime plannedTime;
}
