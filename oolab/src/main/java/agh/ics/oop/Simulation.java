package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation extends Thread {
    private final int numOfAnimals;
    private List<Animal> animals = new ArrayList<>();
    private final AbstractWorldMap map;
    private final Object lock = new Object();
    private final int initialEnergy;
    private final int genomeLength;
    private volatile boolean running = true;

    public Simulation(int numOfAnimals, AbstractWorldMap map, int initialEnergy, int genomeLength) {
        this.numOfAnimals = numOfAnimals;
        this.map = map;
        this.initialEnergy = initialEnergy;
        this.genomeLength=genomeLength;
        addAnimals();
    }

    public List<Animal> getAnimals() {
        return this.animals;
    }

    private void addAnimals() {
        Boundary worldBoundary = map.getCurrentBounds();
        Random random = new Random();
        for (int i = 0; i < numOfAnimals; i++) {
            int x = random.nextInt(worldBoundary.upperRight().getX());
            int y = random.nextInt(worldBoundary.upperRight().getY());
            Vector2d randomPosition = new Vector2d(x, y);

            Animal animal = new Animal(randomPosition, initialEnergy, genomeLength);
            map.place(animal);
            animals.add(animal);
        }
    }

    public void pauseSimulation() {
        running = false;
    }

    public void resumeSimulation() {
        running = true;
        synchronized (lock) {
            lock.notify();
        }
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        try {
            int day = 0;
            while(map.getElements() != null) {
                synchronized (lock) {
                    while (!running) {
                        lock.wait();
                    }
                }
                Thread.sleep(1000);

                // moving
                for (Animal animal : animals) {
                    map.move(animal, animal.getGenomes().getGenes().get(day % animal.getGenomes().getGenes().size()));
                    if(map.getClass().equals(SecretTunnels.class)){
                        ((SecretTunnels) map).wentThroughTunnel(animal, animal.getPosition());
                    }
                    animal.animalEnergyChange(-1);
                }

//                 eating
                Set<Vector2d> grassesToEat = map.getGrassPositions();
                for (Vector2d grassPosition : new HashSet<>(grassesToEat)) {
                    Animal chosenAnimal = map.chooseAnimal(grassPosition);
                    if (chosenAnimal != null) {
                        map.eat(chosenAnimal);
                    }
                }
                map.placeGrass(map.getPlantSpawnRate(), map.getGrassPositions());
                day++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}