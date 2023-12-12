package agh.ics.oop;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine implements Runnable {

    private final ArrayList<Simulation> simulations;
    private final ArrayList<Thread> threads;

    public SimulationEngine(ArrayList<Simulation> simulations) {
        this.simulations = simulations;
        this.threads = new ArrayList<>();
    }

    public void runSync() {
        for (Simulation simulation : simulations) {
            simulation.run();
        }
    }

    public void runAsync() {

        for(Simulation simulation : this.simulations) {
            simulation.start();
        }
    }


    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public void runAsyncInThreadPool() {
        int numThreads = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (Simulation simulation : simulations) {
            executorService.submit(simulation::run);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread started.");
    }
}
