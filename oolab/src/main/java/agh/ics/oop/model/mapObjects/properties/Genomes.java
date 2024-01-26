package agh.ics.oop.model.mapObjects.properties;

import agh.ics.oop.model.mapObjects.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Genomes { // Genome
    private final List<Integer> genes;


    public Genomes(int genomeLength) {
        this.genes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < genomeLength; i++) {
            this.genes.add(random.nextInt(8));
        }
    }

    public Genomes(List<Integer> genes, int mingeneMutation,int maxgeneMutation){
        this.genes = genes;
        Mutate(mingeneMutation, maxgeneMutation);
    }

    public List<Integer> getGenes() {
        return this.genes; // dehermetyzacja
    }

    public List<Integer> GeneMerger(Animal mommy, Animal daddy){  // Panowie widzą, że ta metoda potrzebuje 3 genotypów i zwraca listę?

        Animal strongerParent = mommy.getEnergy() > daddy.getEnergy() ? mommy : daddy;
        Animal weakerParent = mommy.getEnergy() > daddy.getEnergy() ? daddy : mommy;

        double totalEnergy = mommy.getEnergy() + daddy.getEnergy();
        double strongerParentEnergyRatio = strongerParent.getEnergy() / totalEnergy;
        double weakerParentEnergyRatio = weakerParent.getEnergy() / totalEnergy;

        Random random = new Random(); // co wywołanie?
        List<Integer> newGenes = new ArrayList<>();
        boolean side = random.nextBoolean();
        if (side){
            newGenes.addAll(strongerParent.getGenomes().getGenes().subList(0, (int) strongerParentEnergyRatio*strongerParent.getGenomes().getGenes().size()));
            newGenes.addAll(weakerParent.getGenomes().getGenes().subList((int) weakerParentEnergyRatio*weakerParent.getGenomes().getGenes().size(),weakerParent.getGenomes().getGenes().size()));
        } else{
            newGenes.addAll(weakerParent.getGenomes().getGenes().subList(0, (int) weakerParentEnergyRatio*weakerParent.getGenomes().getGenes().size()));
            newGenes.addAll(strongerParent.getGenomes().getGenes().subList((int) strongerParentEnergyRatio*strongerParent.getGenomes().getGenes().size(),strongerParent.getGenomes().getGenes().size()));
        }
        return newGenes;
    }
    public void Mutate(int mingeneMutation,int maxgeneMutation){ // camelCase; public?
        List<Integer> allGenes = new ArrayList<>();
        Random random = new Random(); // co wywołanie?
        int numberOfMutations;
        if(mingeneMutation == maxgeneMutation){
            numberOfMutations = mingeneMutation;
        } else {
            numberOfMutations = random.nextInt(maxgeneMutation - mingeneMutation) + mingeneMutation;
        }
        for(int i = 0; i < this.genes.size(); i++){
            allGenes.add(i);
        }
        Collections.shuffle(allGenes);
        for (int i = 0; i < numberOfMutations; i++){
            this.genes.set(allGenes.get(i), random.nextInt(8));
        }
    }
}