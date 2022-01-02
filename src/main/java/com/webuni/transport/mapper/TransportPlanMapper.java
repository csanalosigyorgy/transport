package com.webuni.transport.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.webuni.transport.dto.SectionDto;
import com.webuni.transport.dto.TransportPlanDto;
import com.webuni.transport.model.Section;
import com.webuni.transport.model.TransportPlan;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

	@Named("toTransportPlanDtoDetailed")
	TransportPlanDto toTransportPlanDtoDetailed(TransportPlan transportPlan);

	@IterableMapping(qualifiedByName = "toTransportPlanDtoDetailed")
	List<TransportPlanDto> toTransportPlanDtosDetailed(List<TransportPlan> transportPlans);

	@Mapping(target = "sections" , qualifiedByName = "toSectionDtoIgnoreAddresses")
	TransportPlanDto toTransportPlanDtoIgnoreAddresses(TransportPlan transportPlan);

	@Named("toSectionDtoIgnoreAddresses")
	@Mapping(target = "fromMilestone.address", ignore = true)
	@Mapping(target = "toMilestone.address", ignore = true)
	SectionDto toSectionDtoIgnoreAddresses(Section section);
}
