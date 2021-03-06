package com.webuni.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {

	private Long id;
	private int number;
	private MilestoneDto fromMilestone;
	private MilestoneDto toMilestone;
}
