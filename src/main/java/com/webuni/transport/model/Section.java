package com.webuni.transport.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Section {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private int number;

	@OneToOne
	private Milestone fromMilestone;

	@OneToOne
	private Milestone toMilestone;

	@ManyToOne
	private TransportPlan transportPlan;
}
