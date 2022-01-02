package com.webuni.transport.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private MilestoneType milestoneType;

	@ManyToOne(fetch = FetchType.LAZY)
	private Address address;

	private LocalDateTime plannedTime;


}
