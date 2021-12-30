package com.webuni.transport.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SectionDto {

	private Long id;
	private int number;
	private MilestoneDto fromMilestone;
	private MilestoneDto toMilestone;
	//private TransportPlanDto transportPlan;
}
