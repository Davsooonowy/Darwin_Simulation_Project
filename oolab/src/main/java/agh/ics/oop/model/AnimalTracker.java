package agh.ics.oop.model;

public class AnimalTracker {
    private Animal trackedAnimal;
    private Genomes genotype;
    private int activeGene;
    private int energy;
    private int eatenPlants;
    private int childrenCount;
    private int descendantsCount;
    private int livingDays;
    private int deathDay;

    public AnimalTracker(Animal animal) {
        this.trackedAnimal = animal;
        this.genotype = animal.getGenomes();
        //this.activeGene = genotype.getActiveGene();
        this.energy = animal.getEnergy();
        //this.eatenPlants = animal.getEatenPlantsCount();
        this.childrenCount = animal.getChildrenCount();
        //this.descendantsCount = animal.getDescendantsCount();
        //this.livingDays = animal.getLivingDays();
        //this.deathDay = animal.isAlive() ? -1 : animal.getDeathDay();
    }

//    public void update() {
//        this.activeGene = genotype.getActiveGene();
//        this.energy = trackedAnimal.getEnergy();
//        this.eatenPlants = trackedAnimal.getEatenPlantsCount();
//        this.childrenCount = trackedAnimal.getChildrenCount();
//        this.descendantsCount = trackedAnimal.getDescendantsCount();
//        this.livingDays = trackedAnimal.getLivingDays();
//        this.deathDay = trackedAnimal.isAlive() ? -1 : trackedAnimal.getDeathDay();
//    }
}
