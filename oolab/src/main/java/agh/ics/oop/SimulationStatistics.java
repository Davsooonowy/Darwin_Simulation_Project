package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
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
    Map<Genomes, Long> genotypesCount = simulation.getAnimals().stream()
            .collect(Collectors.groupingBy(Animal::getGenomes, Collectors.counting()));

    long maxCount = Collections.max(genotypesCount.values());

    return genotypesCount.entrySet().stream()
            .filter(entry -> entry.getValue() == maxCount)
            .map(Map.Entry::getKey)
            .findFirst()
            .map(Genomes::getGenes)
            .orElse(Collections.emptyList());
}



public Genomes getDominantGenotype() {
    return simulation.getAnimals().stream()
            .collect(Collectors.groupingBy(Animal::getGenomes, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
}



    public double getAverageEnergy() {
        return Math.round(simulation.getAnimals().stream()
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0) * 100.0) / 100.0;
    }

    public double getAverageLifeSpan() {
        return Math.round(simulation.getDeadAnimals().stream()
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0) * 100.0) / 100.0;
    }

    public double getAverageChildrenCount() {
        return Math.round(simulation.getAnimals().stream()
                .mapToInt(Animal::getChildrenCount)
                .average()
                .orElse(0) * 100.0) / 100.0;
    }

    @Override
public String toString() {
    return "SimulationStatistics{" +
            "totalAnimals=" + getTotalAnimals() +
            ", totalPlants=" + getTotalPlants() +
            ", freeFields=" + getFreeFields() +
            ", mostCommonGenotypes=" + getMostCommonGenotypes() +
            ", averageEnergy=" + String.format("%.2f", getAverageEnergy()) +
            ", averageLifeSpan=" + String.format("%.2f", getAverageLifeSpan()) +
            ", averageChildrenCount=" + String.format("%.2f", getAverageChildrenCount()) +
            '}';
}
}