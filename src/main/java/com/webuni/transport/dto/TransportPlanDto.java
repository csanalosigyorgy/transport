package com.webuni.transport.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransportPlanDto {

	private Long id;
	private double revenue;
	List<SectionDto> sections;
}
