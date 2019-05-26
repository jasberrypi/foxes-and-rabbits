package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.actors.animals.Animal;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;

import java.util.Iterator;

/**
 * A simple model of a hunter.
 * Hunters move, hunt tigers, foxes and rabbits.
 *
 */
public class Hunter extends Actor {

    public Hunter() {
        alive = true;
    }

    public void hunt(Field currentField, Field updatedField) {
        if (alive) {
            Location newLocation = findAnimals(currentField, location);
            if (newLocation == null) {
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
        }
    }

    private Location findAnimals(Field field, Location location) {
        Iterator adjacentLocations =
                field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Animal) {
                Animal prey = (Animal) animal;
                if (prey.isAlive()) {
                    prey.setEaten();
                    return where;
                }
            }
        }
        return null;
    }
}
