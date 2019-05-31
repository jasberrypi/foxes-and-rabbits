package io.muzoo.ooc.ecosystems.actors.animals;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Fox extends Animal {
    // Characteristics shared by all foxes (static fields).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int PREY_FOOD_VALUE = 4;
    private static final List<Class> LIST_OF_PREY = Arrays.asList(Rabbit.class);
    public static final double CREATION_PROBABILITY = 0.02;
    public static final Color CLASS_COLOR = Color.blue;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox(boolean randomAge) {
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
