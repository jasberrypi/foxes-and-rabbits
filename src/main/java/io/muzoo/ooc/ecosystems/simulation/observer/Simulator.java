package io.muzoo.ooc.ecosystems.simulation.observer;

import io.muzoo.ooc.ecosystems.actors.Actor;
import io.muzoo.ooc.ecosystems.actors.ActorFactory;
import io.muzoo.ooc.ecosystems.actors.Hunter;
import io.muzoo.ooc.ecosystems.actors.Rock;
import io.muzoo.ooc.ecosystems.actors.animals.Fox;
import io.muzoo.ooc.ecosystems.actors.animals.Rabbit;
import io.muzoo.ooc.ecosystems.actors.animals.Tiger;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;

import java.util.*;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing
 * rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Simulator implements Subject{
    // The private static final variables represent 
    // configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 50;

    // The list of actors in the field
    private List actors;
    // The list of actors just born
    private List newActors;
    // The current state of the field.
    protected Field field;
    // A second field, used to build the next stage of the simulation.
    private Field updatedField;
    // The current step of the simulation.
    protected int step;
    private Set<Observer> observers;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        actors = new ArrayList();
        newActors = new ArrayList();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);
        observers = new HashSet<>();

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation() {
        simulate(500);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps; step++) {
            simulateOneStep();
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each animal or actor
     */
    public void simulateOneStep() {
        step++;
        newActors.clear();

        // let all objects act
        for (Iterator iter = actors.iterator(); iter.hasNext(); ) {
            Object object = iter.next();
            if (object instanceof Actor) {
                Actor actor = (Actor) object;
                actor.act(field, updatedField, newActors);
            }else {
                System.out.println("found unknown object");
            }
        }
        // add new born animals to the list of animals
        actors.addAll(newActors);

        // Swap the field and updatedField at the end of the step.
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // display the new field on screen
        notifyAllObservers();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        actors.clear();
        field.clear();
        updatedField.clear();
        populate(field);

        // Show the starting state in the view.
        notifyAllObservers();
    }

    /**
     * Populate a field with actors.
     *
     * @param field The field to be populated.
     */
    private void populate(Field field) {
        field.clear();
        ActorFactory factory = new ActorFactory();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Actor actor = factory.createActor();
                if (actor != null) {
                    actors.add(actor);
                    actor.setLocation(row, col);
                    field.place(actor, row, col);
                }
            }
        }
        Collections.shuffle(actors);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        observer.setSubject(this);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        observer.setSubject(null);
    }
}
