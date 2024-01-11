package agh.ics.oop;

import agh.ics.oop.model.*;


import java.util.*;
import java.util.stream.Collectors;

public class SimulationStatistics {
    private final Simulation simulation;
    private final AbstractWorldMap map;

    public SimulationStatistics(Simulation simulation, AbstractWorldMap map) {
        this.simulation = simulation;
        this.map = map;
    }

    public int getTotalAnimals() {
        return simulation.getAnimals().size();
    }

    public int getTotalPlants() {
        return map.getGrassPositions().size();
    }

    public int getFreeFields() {
        return map.getFreeFields();
    }

    public List<Integer> getMostCommonGenotypes() {
        Map<List<Integer>, Long> genotypesCount = simulation.getAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getGenes, Collectors.counting()));

        long maxCount = Collections.max(genotypesCount.values());

        return genotypesCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public double getAverageEnergy() {
        return simulation.getAnimals().stream()
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0);
    }

    public double getAverageLifeSpan() {
        return simulation.getDeadAnimals().stream()
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0);
    }

    public double getAverageChildrenCount() {
        return simulation.getAnimals().stream()
                .mapToInt(Animal::getChildrenCount)
                .average()
                .orElse(0);
    }
}