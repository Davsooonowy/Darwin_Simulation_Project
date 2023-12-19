package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Simulation extends Thread {
    private  List<Animal> animals = new ArrayList<>();
    private  List<Integer> directions;
    private List<Vector2d> coordinates;
    private WorldMap map;

    private int initialEnergy;
    private int moveEnergy;
    private int plantEnergy;

    public Simulation(List<Integer> directions, List<Vector2d> coordinates, WorldMap map, int initialEnergy, int moveEnergy, int plantEnergy) {
        List<Integer> reversedDirections = new ArrayList<>(directions);
        Collections.reverse(reversedDirections);
        this.directions = directions;
        this.directions.addAll(reversedDirections);
        this.coordinates = coordinates;
        this.map = map;
        this.initialEnergy = initialEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        addAnimals();
    }

    private void addAnimals() {
        for (Vector2d move : coordinates) {
            Animal animal = new Animal(move, initialEnergy);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (PositionAlreadyOccupiedException ignored) {
                System.out.println(ignored.getMessage());
            }
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
                Thread.sleep(1000);
                for (Animal animal : animals) {
//                    if (animal.isDead()) {
//                        map.remove(animal);
//                        animals.remove(animal);
//                    } else {
                        //Integer direction = directions.get(animals.indexOf(animal) % directions.size());
                        map.move(animal, animal.getGenome(day%animal.getGenomesize()));
                        animal.animalEnergyChange(-moveEnergy);
                    }
                day++;
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}