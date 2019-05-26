package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.simulation.simhelpers.Field;

public class Rock extends Actor{

    public Rock(){ alive = true; }

    public void stay(Field currentField, Field updatedField) {
        setLocation(location);
        updatedField.place(this, location);
    }
}
