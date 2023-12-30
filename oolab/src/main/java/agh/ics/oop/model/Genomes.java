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
    public List<Integer> getGenes() {
        return this.genes;
    }

    public Genomes GeneMerger(Animal mommy, Animal daddy){
        int energysum = mommy.getEnergy() + daddy.getEnergy();
        Animal stronger = mommy;
        if (!(mommy.getEnergy() > daddy.getEnergy())){
            stronger = daddy;
        }
    }
}