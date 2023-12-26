package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Simulation extends Thread {
    private final int numOfAnimals;
    private List<Animal> animals = new ArrayList<>();
    private final WorldMap map;
    private final Object lock = new Object();
    private final int initialEnergy;
    private final int moveEnergy;
    private int plantEnergy;

    private volatile boolean running = true;

    public Simulation(int numOfAnimals, WorldMap map, int initialEnergy, int moveEnergy, int plantEnergy) {
        this.numOfAnimals = numOfAnimals;
        this.map = map;
        this.initialEnergy = initialEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
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

            Animal animal = new Animal(randomPosition, initialEnergy, 7);
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

    public void reproduceAnimals() {
        List<Animal> newAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.canReproduce()) {
                List<Animal> possiblePartners = map.objectsAt(animal.getPosition())
                        .stream()
                        .filter(WorldElement::isAnimal)
                        .map(WorldElement::asAnimal)
                        .filter(Animal::canReproduce)
                        .collect(Collectors.toList());
                if (possiblePartners.size() > 1) {
                    possiblePartners.remove(animal);
                    Animal partner = possiblePartners.get(new Random().nextInt(possiblePartners.size()));
                    newAnimals.add(animal.reproduce(partner));
                }
            }
        }
        animals.addAll(newAnimals);
        for (Animal newAnimal : newAnimals) {
            map.place(newAnimal);
        }
    }


//    public void run() {
//        try {
////        for (int i = 0; i < 16; i++) { // TODO: nieskonczona petla ze zwierzakami, dopoki zyja
////            Thread.sleep(1000);
////            Animal animal = animals.get(i % animals.size());
////            Integer direction = directions.get(i % directions.size());
////            map.move(animal, direction);
////        }
//            int day = 0;
//            while(map.getElements() != null) {
//                Thread.sleep(1000);
//                for (Animal animal : animals) {
////                    if (animal.isDead()) {
////                        map.remove(animal);
////                        animals.remove(animal);
////                    } else {
//                        //Integer direction = directions.get(animals.indexOf(animal) % directions.size());
//                        map.move(animal, animal.getGenome(day % animal.getGenomesize()));
//                        if(map.getClass().equals(SecretTunnels.class)){
//                        ((SecretTunnels) map).wentThroughTunnel(animal, animal.getPosition());
//                        }
//                        animal.animalEnergyChange(-moveEnergy);
//                    }
//                day++;
//                }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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
                for (Animal animal : animals) {
                    map.move(animal, animal.getGenomes().getGenes().get(day % animal.getGenomes().getGenes().size()));
                    if(map.getClass().equals(SecretTunnels.class)){
                        ((SecretTunnels) map).wentThroughTunnel(animal, animal.getPosition());
                    }
                    animal.animalEnergyChange(-moveEnergy);
                    System.out.println(animals.size());
                }
                //reproduceAnimals();
                day++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}