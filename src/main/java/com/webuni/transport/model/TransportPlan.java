package com.webuni.transport.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportPlan {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private double revenue;

	@OneToMany(mappedBy = "transportPlan")
	List<Section> sections;
}
