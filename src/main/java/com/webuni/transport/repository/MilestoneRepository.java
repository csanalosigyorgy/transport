package com.webuni.transport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webuni.transport.model.Milestone;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

	@Override
	@EntityGraph(attributePaths = {"section"})
	@Query("SELECT m FROM Milestone m WHERE m.id = ?1")
	Optional<Milestone> findById(Long id);
}
