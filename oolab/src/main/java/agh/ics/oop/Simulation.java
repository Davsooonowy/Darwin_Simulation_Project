package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Simulation extends Thread {
    private final int numOfAnimals;
    private  List<Animal> animals = new ArrayList<>();
    private final WorldMap map;

    private final int initialEnergy;
    private final int moveEnergy;
    private int plantEnergy;

    public Simulation(int numOfAnimals, WorldMap map, int initialEnergy, int moveEnergy, int plantEnergy) {
        this.numOfAnimals = numOfAnimals;
        this.map = map;
        this.initialEnergy = initialEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        addAnimals();
    }

private void addAnimals() {
    Boundary worldBoundary = map.getCurrentBounds();
    Random random = new Random();
    for (int i = 0; i < numOfAnimals; i++) {
        int x = random.nextInt(worldBoundary.upperRight().getX());
        int y = random.nextInt(worldBoundary.upperRight().getY());
        Vector2d randomPosition = new Vector2d(x, y);

        Animal animal = new Animal(randomPosition, initialEnergy);
        map.place(animal);
        animals.add(animal);
    }
}

    public void run() {
        try {
//        for (int i = 0; i < 16; i++) { // TODO: nieskonczona petla ze zwierzakami, dopoki zyja
//            Thread.sleep(1000);
//            Animal animal = animals.get(i % animals.size());
//            Integer direction = directions.get(i % directions.size());
//            map.move(animal, direction);
//        }
            int day = 0;
            while(map.getElements() != null) {
                Thread.sleep(100);
                for (Animal animal : animals) {
//                    if (animal.isDead()) {
//                        map.remove(animal);
//                        animals.remove(animal);
//                    } else {
                        //Integer direction = directions.get(animals.indexOf(animal) % directions.size());
                        map.move(animal, animal.getGenome(day % animal.getGenomesize()));
                        animal.animalEnergyChange(-moveEnergy);
                    }
                day++;
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}