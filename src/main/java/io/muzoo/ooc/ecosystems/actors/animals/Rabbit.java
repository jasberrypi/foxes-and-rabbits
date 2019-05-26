package io.muzoo.ooc.ecosystems.actors.animals;

import io.muzoo.ooc.ecosystems.actors.Actor;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Rabbit extends Animal {
    // Characteristics shared by all rabbits (static fields).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     */
    public Rabbit(boolean randomAge) {
        age = 0;
        alive = true;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    /**
     * This is what the rabbit does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     *
     * @param updatedField The field to transfer to.
     * @param newRabbits   A list to add newly born rabbits to.
     */
    @Override
    public void act(Field currentField, Field updatedField, List<Actor> newRabbits) {
        incrementAge();
        if (alive) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                Location loc = updatedField.freeAdjacentLocation(location);
                if (loc != null){
                    Rabbit newRabbit = new Rabbit(false);
                    newRabbits.add(newRabbit);
                    newRabbit.setLocation(loc);
                    updatedField.place(newRabbit, loc);
                }
            }
            Location newLocation = updatedField.freeAdjacentLocation(location);
            // Only transfer to the updated field if there was a free location
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                alive = false;
            }
        }
    }

    @Override
    protected Class getClassType() {
        return this.getClass();
    }

    @Override
    protected int getPreyFoodValue() {
        return 0;
    }

    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected List<Class> getListOfPrey() {
        return null;
    }
}
