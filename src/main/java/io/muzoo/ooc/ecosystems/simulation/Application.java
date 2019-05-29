package io.muzoo.ooc.ecosystems.simulation;

import io.muzoo.ooc.ecosystems.simulation.observer.Simulator;
import io.muzoo.ooc.ecosystems.simulation.observer.SimulatorView;

public class Application {
    public static void main(String[] args) {
        Simulator sim = new Simulator(100, 180);
        SimulatorView view = new SimulatorView(100, 180);
        sim.addObserver(view);
        sim.simulate(500);
        System.exit(0);
    }

}
