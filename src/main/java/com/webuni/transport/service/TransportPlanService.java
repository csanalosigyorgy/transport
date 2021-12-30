package com.webuni.transport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webuni.transport.dto.TransportPlanDelayDto;
import com.webuni.transport.dto.TransportPlanDto;
import com.webuni.transport.mapper.TransportPlanMapper;
import com.webuni.transport.model.Milestone;
import com.webuni.transport.model.Section;
import com.webuni.transport.model.TransportPlan;
import com.webuni.transport.repository.MilestoneRepository;
import com.webuni.transport.repository.SectionRepository;
import com.webuni.transport.repository.TransportPlanRepository;
import com.webuni.transport.service.base.BaseTransportPlanService;

@Service
public class TransportPlanService implements BaseTransportPlanService {

	@Autowired
	private TransportPlanMapper transportPlanMapper;

	@Autowired
	private TransportPlanRepository transportPlanRepository;

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Override
	public TransportPlanDto saveDelay(Long id, TransportPlanDelayDto delayDto) {
		TransportPlan transportPlan = transportPlanRepository.findById(id)
				.orElseThrow(NoSuchElementException::new);

		Milestone milestone = milestoneRepository.findById(delayDto.getMilestoneId())
				.orElseThrow(NoSuchElementException::new);

		List<Section> sections = transportPlan.getSections(); 

		Map<Integer, Milestone> milestones = new HashMap<>();
		sections.forEach(m ->milestones.put(0, m.getFromMilestone()));
		sections.forEach(m ->milestones.put(1, m.getToMilestone()));

		Optional<Milestone> milestoneOptional = milestones.values().stream().filter(m -> m.getId().equals(milestone.getId())).findAny();

		Milestone milestone1 = milestoneOptional.orElseThrow(IllegalArgumentException::new);



		return null;
	}

	@Override
	public TransportPlanDto getById(Long id) {
		TransportPlan transportPlan = transportPlanRepository.findById(id).orElseThrow(NoSuchElementException::new);
		return transportPlanMapper.toTransportPlanDto(transportPlan);
	}
}













