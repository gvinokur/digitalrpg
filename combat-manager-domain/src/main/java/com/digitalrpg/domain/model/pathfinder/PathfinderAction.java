package com.digitalrpg.domain.model.pathfinder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pathfinder_action")
public class PathfinderAction {

	private Long id;
	
	private String label;
	
	private Boolean current;
	
	private Boolean initial;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}

	public Boolean getInitial() {
		return initial;
	}

	public void setInitial(Boolean initial) {
		this.initial = initial;
	}

	private String description;
	
}
