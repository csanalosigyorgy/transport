package com.webuni.transport.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webuni.transport.dto.TransportPlanDelayDto;
import com.webuni.transport.dto.TransportPlanDto;
import com.webuni.transport.mapper.TransportPlanMapper;
import com.webuni.transport.model.TransportPlan;
import com.webuni.transport.repository.TransportPlanRepository;
import com.webuni.transport.service.base.BaseTransportPlanService;

@RestController
@RequestMapping("/transport-plans")
public class TransportPlanController {

	@Autowired
	private BaseTransportPlanService transportPlanService;

	@PostMapping("/{id}/delay")
	public TransportPlanDto saveDelay(@PathVariable Long id, @RequestBody TransportPlanDelayDto delayDto){
		return transportPlanService.saveDelay(id, delayDto);
	}

	@GetMapping
	public List<TransportPlan> getAllTransportPlans(){
		return null;
	}

	@GetMapping("/{id}")
	public TransportPlanDto getTransportPlanById(@PathVariable Long id){
		return transportPlanService.getById(id);
	}
}
