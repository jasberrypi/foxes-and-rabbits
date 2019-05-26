package io.muzoo.ooc.ecosystems.simulation.observer;

public interface Observer {

	void update();

	void setSubject(Subject subject);
}
