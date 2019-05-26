package io.muzoo.ooc.ecosystems.actors;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;

import java.util.Random;

public abstract class Animal{
    // A shared random number generator to control breeding.
    protected final Random rand = new Random();

    protected int age;
    protected boolean alive;
    protected Location location;

    protected void incrementAge() {
        age++;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setEaten() {
        alive = false;
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
