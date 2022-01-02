package com.webuni.transport.repository;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webuni.transport.model.Milestone;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

	@Override
	@EntityGraph(attributePaths = {"section"})
	@Query("SELECT m FROM Milestone m WHERE m.id = ?1")
	@NotNull
	Optional<Milestone> findById(@NotNull Long id);

	@Query("SELECT DISTINCT m FROM Milestone m LEFT JOIN FETCH Section s WHERE s.id = :sectionId AND s.number = :number")
	Optional<Milestone>findBySectionIdAndNumber(Long sectionId, int number);
}
