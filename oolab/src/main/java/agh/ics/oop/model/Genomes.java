package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genomes {
    private final List<Integer> genes;

    public Genomes(int genomeLength) {
        this.genes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < genomeLength; i++) {
            this.genes.add(random.nextInt(8));
        }
    }

    public Genomes(List<Integer> genes) {
            this.genes = new ArrayList<>(genes);
    }

    public Genomes crossover(Genomes other) {
        int crossoverPoint = this.genes.size() / 2;
        List<Integer> childGenes = new ArrayList<>();

        childGenes.addAll(this.genes.subList(0, crossoverPoint));
        childGenes.addAll(other.genes.subList(crossoverPoint, other.genes.size()));

        return new Genomes(childGenes);
    }

    public void mutate() {
        Random random = new Random();
        int numOfMutations = random.nextInt(this.genes.size());

        for (int i = 0; i < numOfMutations; i++) {
            int mutationPoint = random.nextInt(this.genes.size());
            this.genes.set(mutationPoint, random.nextInt(8));
        }
    }
}

    public List<Integer> getGenes() {
        return this.genes;
    }
}