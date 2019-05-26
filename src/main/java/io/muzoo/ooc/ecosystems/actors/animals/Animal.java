package io.muzoo.ooc.ecosystems.actors.animals;

import io.muzoo.ooc.ecosystems.actors.Actor;

import java.util.Random;

public abstract class Animal extends Actor {
    // A shared random number generator to control breeding.
    protected final Random rand = new Random();

    protected int age;

    protected void incrementAge() {
        age++;
    }

    public void setEaten() {
        alive = false;
    }
}
