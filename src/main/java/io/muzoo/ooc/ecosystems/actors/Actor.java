package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;


public abstract class Actor {
    protected boolean alive;
    protected Location location;

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
