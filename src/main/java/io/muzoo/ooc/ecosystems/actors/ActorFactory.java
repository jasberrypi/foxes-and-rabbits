package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.actors.animals.Fox;
import io.muzoo.ooc.ecosystems.actors.animals.Rabbit;
import io.muzoo.ooc.ecosystems.actors.animals.Tiger;

import java.util.*;
import java.lang.reflect.InvocationTargetException;


public class ActorFactory {

    public static Map<String,Class> actorsMap = new LinkedHashMap<String, Class>(){{
        put("hunter", Hunter.class);
        put("rock", Rock.class);
        put("tiger", Tiger.class);
        put("fox", Fox.class);
        put("rabbit", Rabbit.class);
    }};

    public static Actor createActor() {
        double randomNum = Math.random(); // generates random number between 0 & 1
        for (String className : actorsMap.keySet()) {
            Class type = actorsMap.get(className);
            Object prob = null;
            try {
                prob = type.getDeclaredField("CREATION_PROBABILITY").get(null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            double probNum = (double)prob;
            if (randomNum < probNum) {
                Actor newActor = null;
                try {
                    newActor = (Actor) type.getDeclaredConstructor(boolean.class).newInstance(true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return newActor;

            } else {
                randomNum = randomNum - probNum;
            }
        }
        return null;
    }

}
