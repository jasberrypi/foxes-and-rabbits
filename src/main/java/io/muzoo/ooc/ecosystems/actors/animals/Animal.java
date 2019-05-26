package io.muzoo.ooc.ecosystems.actors.animals;

import io.muzoo.ooc.ecosystems.actors.Actor;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
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

    @Override
    public void act(Field currentField, Field updatedField, List<Actor> newAnimals) {
        incrementAge();
        incrementHunger();
        if (alive) {
            // New animals are born into adjacent locations.
            int births = breed();
            for (int b = 0; b < births; b++) {
                Location loc = updatedField.freeAdjacentLocation(location);
                if (loc != null){
                    Class type = getClassType();
                    Animal newAnimal = null;
                    try {
                        newAnimal = (Animal) type.getDeclaredConstructor(boolean.class).newInstance(false);
                        newAnimals.add(newAnimal);
                        newAnimal.setLocation(loc);
                        updatedField.place(newAnimal, loc);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Move towards prey if found.
            Location newLocation = findPrey(currentField, location);
            if (newLocation == null) {  // no prey found - move randomly
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

    /**
     * Tell the animal to look for prey adjacent to its current location.
     *
     * @param field    The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where prey was found, or null if it wasn't.
     */
    protected Location findPrey(Field field, Location location) {
        Iterator adjacentLocations =
                field.adjacentLocations(location);
        List<Class> ListOfPrey = getListOfPrey();
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object actor = field.getObjectAt(where);
            if (ListOfPrey!= null && actor!= null && ListOfPrey.contains(actor.getClass())) {
                Actor prey = (Actor) actor;
                if (prey.isAlive()) {
                    prey.setDead();
                    foodLevel = getPreyFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    protected abstract Class getClassType();

    protected abstract int getPreyFoodValue();

}
