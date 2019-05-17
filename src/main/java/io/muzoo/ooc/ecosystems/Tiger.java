package io.muzoo.ooc.ecosystems;

import java.util.Iterator;
import java.util.List;

/**
 * A simple model of a tiger.
 * Tigers age, move, eat foxes and rabbits, and die.
 *
 */
public class Tiger extends Animal{
    private static final int BREEDING_AGE = 15;
    private static final int MAX_AGE = 200;
    private static final double BREEDING_PROBABILITY = 0.04;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int RABBIT_FOOD_VALUE = 2;
    private static final int FOX_FOOD_VALUE = 8;

    private int foodLevel;

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

    public void hunt(Field currentField, Field updatedField, List newTigers) {
        incrementAge();
        incrementHunger();
        if (alive) {
            // New foxes are born into adjacent locations.
            int births = breed();
            for (int b = 0; b < births; b++) {
                Tiger newTiger = new Tiger(false);
                newTigers.add(newTiger);
                Location loc = updatedField.randomAdjacentLocation(location);
                newTiger.setLocation(loc);
                updatedField.place(newTiger, loc);
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

    @Override
    protected void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            alive = false;
        }
    }

    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            alive = false;
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
                    rabbit.setEaten();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setEaten();
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    private int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private boolean canBreed() {
        return age >= BREEDING_AGE;
    }
}
