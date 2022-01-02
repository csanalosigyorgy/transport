package com.webuni.transport.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportPlan {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private double revenue;

	@OneToMany(mappedBy = "transportPlan")
	Set<Section> sections;

	public void addSection(Section section){
		if(Objects.isNull(this.sections)){
			this.sections = new HashSet<>();
		}
		this.sections.add(section);
	}
}
