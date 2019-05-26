package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.actors.animals.Animal;
import io.muzoo.ooc.ecosystems.actors.animals.Fox;
import io.muzoo.ooc.ecosystems.actors.animals.Rabbit;
import io.muzoo.ooc.ecosystems.actors.animals.Tiger;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;
import io.muzoo.ooc.ecosystems.simulation.simhelpers.Location;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A simple model of a hunter.
 * Hunters move, hunt tigers, foxes and rabbits.
 *
 */
public class Hunter extends Actor {
    private static final List<Class> LIST_OF_PREY = Arrays.asList(Rabbit.class, Fox.class, Tiger.class);
    public static final double CREATION_PROBABILITY = 0.0001;

    public Hunter(boolean randomAge) {
        alive = true;
    }

    @Override
    protected List<Class> getListOfPrey() {
        return LIST_OF_PREY;
    }
}
