package com.webuni.transport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.webuni.transport.model.TransportPlan;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

	@Override
	@EntityGraph(attributePaths = {
			"sections",
			"sections.fromMilestone",
			"sections.fromMilestone.address",
			"sections.toMilestone",
			"sections.toMilestone.address"})
	@Query("SELECT t FROM TransportPlan t")
	List<TransportPlan> findAll();

	@Override
	@EntityGraph(attributePaths = {
			"sections",
			"sections.fromMilestone",
			"sections.fromMilestone.address",
			"sections.toMilestone",
			"sections.toMilestone.address"})
	@Query("SELECT t FROM TransportPlan t WHERE t.id = ?1")
	Optional<TransportPlan> findById(Long id);
}