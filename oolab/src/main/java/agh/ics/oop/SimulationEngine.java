package agh.ics.oop;

import java.util.ArrayList;
import java.util.UUID;

public class SimulationEngine implements Runnable {
    private final ArrayList<Simulation> simulations;

    public SimulationEngine(ArrayList<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runAsync() {

        for (Simulation simulation : this.simulations) {
            simulation.start();
        }
    }

    public ArrayList<Simulation> getSimulations() {
        return simulations;
    }
    public void pauseSimulation() {
        for (Simulation simulation : this.simulations) {
            simulation.pauseSimulation();
        }
    }

    public void resumeSimulation() {
        for (Simulation simulation : this.simulations) {
            simulation.resumeSimulation();
        }
    }

    public boolean isRunning() {
        for (Simulation simulation : this.simulations) {
            if (simulation.isRunning()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        System.out.println("Thread started.");
    }
}