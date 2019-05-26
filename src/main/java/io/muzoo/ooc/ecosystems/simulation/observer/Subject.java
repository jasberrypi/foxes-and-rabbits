package io.muzoo.ooc.ecosystems.simulation.observer;

public interface Subject {

	void notifyAllObservers();

	void addObserver(Observer observer);

	void removeObserver(Observer observer);

}
