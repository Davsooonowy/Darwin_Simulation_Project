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

    public List<Integer> getGenes() {
        return this.genes;
    }
}