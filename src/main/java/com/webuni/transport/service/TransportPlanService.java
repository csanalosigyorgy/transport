package com.webuni.transport.service;

import static com.webuni.transport.model.MilestoneType.FROM;
import static com.webuni.transport.model.MilestoneType.TO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webuni.transport.configuration.TransportProperty;
import com.webuni.transport.dto.TransportPlanDelayDto;
import com.webuni.transport.model.Milestone;
import com.webuni.transport.model.MilestonesOfSection;
import com.webuni.transport.model.TransportPlan;
import com.webuni.transport.repository.SectionRepository;
import com.webuni.transport.repository.TransportPlanRepository;

@Service
public class TransportPlanService {

	@Autowired
	private TransportProperty transportProperty;

	@Autowired
	private TransportPlanRepository transportPlanRepository;

	@Autowired
	private SectionRepository sectionRepository;

	@Transactional
	public TransportPlan saveDelay(Long id, TransportPlanDelayDto delayDto) {
		TransportPlan transportPlan = getById(id);
		List<MilestonesOfSection> milestonesOfSections = sectionRepository.findMilestonesOfSectionUsingTransportPlanId(transportPlan.getId());

		MilestonesOfSection milestonesOfSection = milestonesOfSections.stream()
				.filter(m -> m.getMilestone().getId().equals(delayDto.getMilestoneId()))
				.findAny()
				.orElseThrow(() -> new NoSuchElementException("This milestone is not part of this transport plan."));
		Milestone milestone = milestonesOfSection.getMilestone();
		if(isFromMilestone(milestone)) {
			milestonesOfSections.stream()
					.filter(ms -> ms.getNumber().equals(milestonesOfSection.getNumber()))
					.forEach(m -> m.getMilestone().setPlannedTime(m.getMilestone().getPlannedTime().plusMinutes(delayDto.getDelayLength())));
		} else if (isToMilestone(milestone)) {
			int nextNumber = milestonesOfSection.getNumber() + 1;
			Milestone nextMilestone = milestonesOfSections.stream()
					.filter(ms -> ms.getNumber().equals(nextNumber) &&
							ms.getMilestone().getMilestoneType().equals(FROM)).map(MilestonesOfSection::getMilestone)
					.findAny()
					.orElseThrow(() -> new NoSuchElementException("The posted milestone was the last in this transport plan."));
			nextMilestone.setPlannedTime(nextMilestone.getPlannedTime().plusMinutes(delayDto.getDelayLength()));
		} else {
			throw new IllegalArgumentException("This milestone has unknown milestone type.");
		}
		transportPlan.setRevenue(getDeductedRevenue(transportPlan, delayDto));
		return transportPlan;
	}

	private boolean isToMilestone(Milestone milestone) {
		return Objects.nonNull(milestone.getMilestoneType()) && milestone.getMilestoneType().equals(TO);
	}

	private boolean isFromMilestone(Milestone milestone) {
		return Objects.nonNull(milestone.getMilestoneType()) && milestone.getMilestoneType().equals(FROM);
	}

	private double getDeductedRevenue(TransportPlan transportPlan, TransportPlanDelayDto delayDto){
		TreeMap<Integer, Double> limits = transportProperty.getDelay().getLimits();
		Optional<Integer> keyOptional = getLimitKey(delayDto, limits);
		double deduction = keyOptional.isPresent() ? limits.get(keyOptional.get()): 0;
		return getDeductionPercentage(transportPlan.getRevenue(), deduction);
	}

	@NotNull
	private Optional<Integer> getLimitKey(TransportPlanDelayDto delayDto, TreeMap<Integer, Double> limits) {
		return limits.keySet().stream()
				.filter(l -> delayDto.getDelayLength() >= l)
				.max(Double::compare);
	}

	private double getDeductionPercentage(double revenue, double deduction){
		return revenue * (1 - deduction / 100);
	}

	public List<TransportPlan> getAll() {
		return transportPlanRepository.findAll();
	}

	public TransportPlan getById(Long id) {
		return transportPlanRepository.findByIdDetailed(id)
				.orElseThrow(() -> new NoSuchElementException("This transport plan does not exist."));
	}

}













