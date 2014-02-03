package com.digitalrpg.domain.model;

public interface SystemAction {

	public abstract Boolean getFinished();

	public abstract Boolean getInitial();

	public abstract Boolean getCurrent();

	public abstract String getDescription();

	public abstract String getLabel();

	public abstract Long getId();

}
