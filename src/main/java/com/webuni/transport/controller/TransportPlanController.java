package com.webuni.transport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webuni.transport.dto.TransportPlanDelayDto;
import com.webuni.transport.dto.TransportPlanDto;
import com.webuni.transport.mapper.TransportPlanMapper;
import com.webuni.transport.service.TransportPlanService;

@RestController
@RequestMapping("/api/transport-plans")
public class TransportPlanController {

	@Autowired
	private TransportPlanMapper transportPlanMapper;

	@Autowired
	private TransportPlanService transportPlanService;

	@PostMapping("/{id}/delay")
	@PreAuthorize("hasAuthority('TransportManager')")
	public TransportPlanDto saveDelay(@PathVariable Long id, @RequestBody TransportPlanDelayDto delayDto){
		return transportPlanMapper.toTransportPlanDtoDetailed(transportPlanService.saveDelay(id, delayDto));
	}

	@GetMapping
	public List<TransportPlanDto> getAllTransportPlans(){
		return transportPlanMapper.toTransportPlanDtosDetailed(transportPlanService.getAll());
	}

	@GetMapping("/{id}")
	public TransportPlanDto getTransportPlanById(@PathVariable Long id){
		return transportPlanMapper.toTransportPlanDtoDetailed(transportPlanService.getById(id));
	}
}
