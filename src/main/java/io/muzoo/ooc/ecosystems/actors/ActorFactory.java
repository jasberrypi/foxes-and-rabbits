package io.muzoo.ooc.ecosystems.actors;

import io.muzoo.ooc.ecosystems.actors.animals.Fox;
import io.muzoo.ooc.ecosystems.actors.animals.Rabbit;
import io.muzoo.ooc.ecosystems.actors.animals.Tiger;

import java.util.*;
import java.lang.reflect.InvocationTargetException;


public class ActorFactory {

    public static Map<Class,Double> probabilitiesMap = new LinkedHashMap<Class,Double>(){{
        put(Hunter.class, 0.0001);
        put(Rock.class, 0.005);
        put(Tiger.class, 0.01);
        put(Fox.class, 0.02);
        put(Rabbit.class, 0.08);
    }};

    public static Actor createActor() {
        double randomNum = Math.random(); // generates random number between 0 & 1
        for (Class type : probabilitiesMap.keySet()) {
            Double probNum = probabilitiesMap.get(type);
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
