package io.muzoo.ooc.ecosystems.actors.animals;

import io.muzoo.ooc.ecosystems.actors.Actor;

import java.util.Random;

public abstract class Animal extends Actor {
    // A shared random number generator to control breeding.
    protected final Random rand = new Random();

    protected int age;
    protected int foodLevel;

    public void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            alive = false;
        }
    }

    public void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            alive = false;
        }
    }

    public int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    public boolean canBreed() {
        return age >= getBreedingAge();
    }

    protected abstract int getMaxAge();

    protected abstract double getBreedingProbability();

    protected abstract int getMaxLitterSize();

    protected abstract int getBreedingAge();
}
