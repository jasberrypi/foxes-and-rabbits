package io.muzoo.ooc.ecosystems.actors.animals;

import io.muzoo.ooc.ecosystems.actors.Actor;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A simple model of a tiger.
 * Tigers age, move, eat foxes and rabbits, and die.
 *
 */
public class Tiger extends Animal {
    private static final int BREEDING_AGE = 15;
    private static final int MAX_AGE = 200;
    private static final double BREEDING_PROBABILITY = 0.04;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int PREY_FOOD_VALUE = 5;
    private static final List<Class> LIST_OF_PREY = Arrays.asList(Rabbit.class,Fox.class);
    public static final double CREATION_PROBABILITY = 0.01;

    public Tiger(boolean randomAge) {
        age = 0;
        alive = true;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(PREY_FOOD_VALUE);
        } else {
            // leave age at 0
            foodLevel = PREY_FOOD_VALUE;
        }
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
    protected Class getClassType() {
        return this.getClass();
    }

    @Override
    protected int getPreyFoodValue() {
        return PREY_FOOD_VALUE;
    }

    @Override
    protected List<Class> getListOfPrey() {
        return LIST_OF_PREY;
    }
}
