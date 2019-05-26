package io.muzoo.ooc.ecosystems.simulation;

import io.muzoo.ooc.ecosystems.actors.Hunter;
import io.muzoo.ooc.ecosystems.actors.Rock;
import io.muzoo.ooc.ecosystems.actors.animals.Fox;
import io.muzoo.ooc.ecosystems.actors.animals.Rabbit;
import io.muzoo.ooc.ecosystems.actors.animals.Tiger;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing
 * rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Simulator {
    // The private static final variables represent 
    // configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 50;
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.0001;
    // The probability that a rock will be created in any given grid position.
    private static final double ROCK_CREATION_PROBABILITY = 0.005;
    // The probability that a tiger will be created in any given grid position.
    private static final double TIGER_CREATION_PROBABILITY = 0.01;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;

    // The list of objects in the field
    private List objects;
    // The list of actors just born
    private List newActors;
    // The current state of the field.
    private Field field;
    // A second field, used to build the next stage of the simulation.
    private Field updatedField;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

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
        objects = new ArrayList();
        newActors = new ArrayList();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Hunter.class, Color.black);
        view.setColor(Rock.class, Color.gray);
        view.setColor(Tiger.class, Color.red);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Rabbit.class, Color.orange);

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
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
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
        for (Iterator iter = objects.iterator(); iter.hasNext(); ) {
            Object object = iter.next();
            if (object instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) object;
                rabbit.act(field, updatedField, newActors);
            } else if (object instanceof Fox) {
                Fox fox = (Fox) object;
                fox.act(field, updatedField, newActors);
            } else if (object instanceof Tiger) {
                Tiger tiger = (Tiger) object;
                tiger.act(field, updatedField, newActors);
            } else if (object instanceof Rock) {
                Rock rock = (Rock) object;
                rock.act(field, updatedField, newActors);
            } else if (object instanceof Hunter) {
                Hunter hunter = (Hunter) object;
                hunter.act(field, updatedField, newActors);
            }else {
                System.out.println("found unknown animal");
            }
        }
        // add new born animals to the list of animals
        objects.addAll(newActors);

        // Swap the field and updatedField at the end of the step.
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // display the new field on screen
        view.showStatus(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        objects.clear();
        field.clear();
        updatedField.clear();
        populate(field);

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Populate a field with foxes and rabbits.
     *
     * @param field The field to be populated.
     */
    private void populate(Field field) {
        Random rand = new Random();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                if (rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                    Hunter hunter = new Hunter();
                    objects.add(hunter);
                    hunter.setLocation(row, col);
                    field.place(hunter, row, col);
                }else if (rand.nextDouble() <= ROCK_CREATION_PROBABILITY) {
                    Rock rock = new Rock();
                    objects.add(rock);
                    rock.setLocation(row, col);
                    field.place(rock, row, col);
                }else if (rand.nextDouble() <= TIGER_CREATION_PROBABILITY) {
                    Tiger tiger = new Tiger(true);
                    objects.add(tiger);
                    tiger.setLocation(row, col);
                    field.place(tiger, row, col);
                } else if (rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    objects.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                } else if (rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    objects.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                }
                // else leave the location empty.
            }
        }
        Collections.shuffle(objects);
    }
}
