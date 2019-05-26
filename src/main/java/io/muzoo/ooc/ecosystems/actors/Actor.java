package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;

import java.util.Iterator;
import java.util.List;


public abstract class Actor {
    protected boolean alive;
    protected Location location;

    public boolean isAlive() {
        return alive;
    }

    public void setDead() {
        alive = false;
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void act(Field currentField, Field updatedField, List<Actor> newActors) {
        if (alive) {
            Location newLocation = findPrey(currentField, location);
            if (newLocation == null) {
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
        }
    }

    private Location findPrey(Field field, Location location) {
        Iterator adjacentLocations =
                field.adjacentLocations(location);
        List<Class> ListOfPrey = getListOfPrey();
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object actor = field.getObjectAt(where);
            if (actor!= null && ListOfPrey.contains(actor.getClass())) {
                Actor prey = (Actor) actor;
                if (prey.isAlive()) {
                    prey.setDead();
                    return where;
                }
            }
        }
        return null;
    }

    protected abstract List<Class> getListOfPrey();
}
