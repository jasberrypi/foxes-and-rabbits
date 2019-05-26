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
    private static final int RABBIT_FOOD_VALUE = 2;
    private static final int FOX_FOOD_VALUE = 8;
    private static final List<Class> LIST_OF_PREY = Arrays.asList(Rabbit.class,Fox.class);

    public Tiger(boolean randomAge) {
        age = 0;
        alive = true;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(FOX_FOOD_VALUE);
        } else {
            // leave age at 0
            foodLevel = FOX_FOOD_VALUE;
        }
    }

    @Override
    public void act(Field currentField, Field updatedField, List<Actor> newTigers) {
        incrementAge();
        incrementHunger();
        if (alive) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                Location loc = updatedField.freeAdjacentLocation(location);
                if (loc != null){
                    Tiger newTiger = new Tiger(false);
                    newTigers.add(newTiger);
                    newTiger.setLocation(loc);
                    updatedField.place(newTiger, loc);
                }
            }
            // Move towards the source of food if found.
            Location newLocation = findFood(currentField, location);
            if (newLocation == null) {  // no food found - move randomly
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                alive = false;
            }
        }
    }

    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations =
                field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
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
        return LIST_OF_PREY;
    }
}
