package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

import agh.ics.oop.model.mapObjects.Animal;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.maps.SecretTunnels;
import agh.ics.oop.model.vector_records.Boundary;
import agh.ics.oop.model.vector_records.Vector2d;

public class Simulation extends Thread {
    ///                                                   fields                                                 ///
    private final int numOfAnimals;
    private final int parentEnergy;
    private List<Animal> animals = new ArrayList<>();
    private List<Animal> deadAnimals = new ArrayList<>();
    private final AbstractWorldMap map;
    private final Object lock = new Object();
    private final int initialEnergy;
    private final int genomeLength;
    private final int reproductionEnergy;
    private final int mingeneMutation;
    private final int maxgeneMutation;
    private final String behaviourvariant;
    private volatile boolean running = true;
    HashSet<Animal> ancestors = new HashSet<>();
    HashMap<Vector2d, List<Animal>> groupedAnimals = new HashMap<>();
    private int day = 0;

    ///                                                constructor                                          ///

    public Simulation(int numOfAnimals, AbstractWorldMap map, int initialEnergy, int genomeLength, int reproductionEnergy, int parentEnergy, int mingeneMutation,int maxgeneMutation, String behaviourvariant) {
        this.numOfAnimals = numOfAnimals;
        this.map = map;
        this.initialEnergy = initialEnergy;
        this.genomeLength=genomeLength;
        this.reproductionEnergy = reproductionEnergy;
        this.parentEnergy=parentEnergy;
        this.mingeneMutation=mingeneMutation;
        this.maxgeneMutation=maxgeneMutation;
        this.behaviourvariant = behaviourvariant;
        addInitialAnimals();
    }

    ///                                                getters                                                 ///

    public List<Animal> getAnimals() {
        return this.animals; // dehermetyzacja
    }

    public List<Animal> getDeadAnimals() {
        return this.deadAnimals;  // dehermetyzacja
    }

    public int getCurrentDay(){
        return this.day;
    }


    ///                                                add first animals                                   ///
    private void addInitialAnimals() {
        Boundary worldBoundary = map.getBounds();
        Random random = new Random();
        for (int i = 0; i < numOfAnimals; i++) {
            int x = random.nextInt(worldBoundary.upperRight().x()+1);
            int y = random.nextInt(worldBoundary.upperRight().y()+1);
            Vector2d randomPosition = new Vector2d(x, y);

            Animal animal = new Animal(randomPosition, initialEnergy, genomeLength, reproductionEnergy, parentEnergy,mingeneMutation,maxgeneMutation);
            map.place(animal);
            animals.add(animal);
        }
    }


    ///                                     start/stop methods                                         ///
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


    ///                                     reproducing                                                    ///
    public void groupAndReproduceAnimals() {
        groupedAnimals.clear();
        for (Animal animal : animals) {
            if (animal.getEnergy() >= reproductionEnergy) {
                Vector2d position = animal.position();
                if (!groupedAnimals.containsKey(position)) {
                    groupedAnimals.put(position, new ArrayList<>());
                }
                groupedAnimals.get(position).add(animal);
            }
        }

        for (List<Animal> group : groupedAnimals.values()) {
            if (group.size() >= 2) {

                Animal parent1 = map.chooseTopAnimal(group);
                group.remove(parent1);
                Animal parent2 = map.chooseTopAnimal(group);

                Animal child = parent1.reproduce(parent2);
                animals.add(child);
                map.place(child);
                ancestors.clear();
                child.offspringincrease(ancestors);
            }
        }
    }


    ///                                     moving                                                            ///
    void move_animals(int day){
        for (Animal animal : animals) {
            if(behaviourvariant.equals("Complete predestination")) {
                map.move(animal, animal.getGenomes().getGenes().get(day % genomeLength));
            } else{
                if((day/genomeLength)%2==0){
                    map.move(animal,animal.getGenomes().getGenes().get(day % genomeLength));
                } else {
                    map.move(animal,animal.getGenomes().getGenes().get(genomeLength - 1 - (day % genomeLength)));
                }
            }
            if (map.getClass().equals(SecretTunnels.class)) { // czy to jest miejsce na obsługę specyfiki konkretnej mapy?
                ((SecretTunnels) map).wentThroughTunnel(animal, animal.position());
            }
            animal.animalEnergyChange(-1);
            animal.increaseAge();
        }
    }

    ///                                      eating                                                             ///
    void eat() {
        if (!animals.isEmpty()) {
            Set<Vector2d> grassesToEat = map.getGrassPositions();
            for (Vector2d grassPosition : new HashSet<>(grassesToEat)) {
                Animal chosenAnimal = map.chooseAnimal(grassPosition);
                if (chosenAnimal != null) {
                    map.eat(chosenAnimal);
                    chosenAnimal.addEeatenPlants();
                }
            }
        }
    }

    ///                                      garbage disposal                                            ///
    void removeDeadBodies(){
        for (Animal animal : animals) {
            if (animal.isDead()) {
                deadAnimals.add(animal);
                animal.setDeathDay(day);
                map.removeDeadAnimal(animal);
            }
        }
        animals = animals.stream().filter(animal -> !animal.isDead()).collect(Collectors.toList());
    }


    ///                                      simulation run                                                ///
    @Override
    public void run() {
        try {
            while(!isInterrupted()) { // ta klasa dziedziczy z Thread
                synchronized (lock) {
                    while (!running) {
                        lock.wait();
                    }
                }

                Thread.sleep(300);
                removeDeadBodies();
                move_animals(day);
                eat();
                groupAndReproduceAnimals();
                Thread.sleep(300);
                map.placeGrass(map.getPlantSpawnRate(), map.getGrassPositions());
                day++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // ?
        }
    }
}