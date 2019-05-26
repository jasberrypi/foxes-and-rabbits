package io.muzoo.ooc.ecosystems.actors.animals;

import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;
import io.muzoo.ooc.ecosystems.actors.Animal;

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
    // A shared random number generator to control breeding.

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
    public void run(Field updatedField, List newRabbits) {
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

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    @Override
    protected void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            alive = false;
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    private int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     *
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed() {
        return age >= BREEDING_AGE;
    }
}
