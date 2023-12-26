package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genomes {
    private ArrayList<Integer> genes;
    private static final Random RANDOM = new Random();

    public Genomes(int size) {
        this.genes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.genes.add(RANDOM.nextInt(8));
        }
        Collections.sort(this.genes);
    }

    public Genomes(ArrayList<Integer> genes) {
        this.genes = genes;
        Collections.sort(this.genes);
    }

    public void mutate() {
        int index = RANDOM.nextInt(this.genes.size());
        this.genes.set(index, RANDOM.nextInt(8));
        Collections.sort(this.genes);
    }

    public static Genomes crossover(Genomes parent1, Genomes parent2) {
        ArrayList<Integer> childGenes = new ArrayList<>();
        int crossoverPoint = RANDOM.nextInt(parent1.genes.size());

        childGenes.addAll(parent1.genes.subList(0, crossoverPoint));
        childGenes.addAll(parent2.genes.subList(crossoverPoint, parent2.genes.size()));

        return new Genomes(childGenes);
    }

    public ArrayList<Integer> getGenes() {
        return this.genes;
    }
}