package com.webuni.transport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webuni.transport.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
