package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;

import java.util.List;

public class Rock extends Actor{
    public static final double CREATION_PROBABILITY = 0.005;

    public Rock(boolean randomAge){
        alive = true;
    }

    @Override
    public void act(Field currentField, Field updatedField, List<Actor> newRocks) {
        setLocation(location);
        updatedField.place(this, location);
    }

    @Override
    protected List<Class> getListOfPrey() {
        return null;
    }
}
