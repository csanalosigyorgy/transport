package com.webuni.transport.service.base;

import com.webuni.transport.dto.TransportPlanDelayDto;
import com.webuni.transport.dto.TransportPlanDto;

public interface BaseTransportPlanService {

	TransportPlanDto saveDelay(Long id, TransportPlanDelayDto delayDto);

	TransportPlanDto getById(Long id);
}
