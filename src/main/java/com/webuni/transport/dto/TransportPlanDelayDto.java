package com.webuni.transport.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportPlanDelayDto {

	@NotEmpty
	private Long milestoneId;
	@Min(1)
	private int delayLength;
}
