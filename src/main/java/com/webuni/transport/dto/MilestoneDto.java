package com.webuni.transport.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MilestoneDto {

	private Long id;
	private AddressDto address;
	private LocalDateTime plannedTime;
}
