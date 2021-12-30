package com.webuni.transport.mapper;

import org.mapstruct.Mapper;

import com.webuni.transport.dto.TransportPlanDto;
import com.webuni.transport.model.TransportPlan;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

	TransportPlanDto toTransportPlanDto(TransportPlan transportPlan);
}
