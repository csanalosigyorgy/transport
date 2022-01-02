package com.webuni.transport.repository;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.webuni.transport.model.MilestonesOfSection;
import com.webuni.transport.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

	@Query("SELECT s.number AS number, m AS milestone " +
				"FROM Milestone m " +
				"LEFT JOIN FETCH Section s ON s.toMilestone.id = m.id OR s.fromMilestone.id = m.id " +
				"LEFT JOIN FETCH TransportPlan tp ON tp.id = s.transportPlan.id " +
				"WHERE tp.id = :transportPlanId ORDER BY s.number")
	List<MilestonesOfSection> findMilestonesOfSectionUsingTransportPlanId(Long transportPlanId);

	@Override
	@EntityGraph(attributePaths = {"fromMilestone", "toMilestone", "transportPlan"})
	@Query("SELECT s FROM Section s WHERE s.id = :id")
	@NotNull
	Optional<Section> findById(@NonNull Long id);
}
