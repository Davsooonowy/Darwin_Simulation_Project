package agh.ics.oop;
import agh.ics.oop.model.Animal;


// TODO: -generator pliku CSV (niedzialajcy checkobx, generoawnie jest)
//TODO: -czyszczenie kodu (clean code)
//TODO: -getActivePart()
//TODO: -optymalizacja
//TODO: -podświetlanie zwierzaków i trawy na zawołanie
//TODO: -testy

public class AnimalStatistics {
    private final Animal animal;
    private final Simulation simulation;

    public AnimalStatistics(Animal animal, Simulation simulation) {
        this.animal = animal;
        this.simulation = simulation;
    }

    public String getGenome() {
        return animal.getGenomes().getGenes().toString();
    }

    public int getActivePart() { //TODO: implement this method
        // Implement this method based on your Animal class
        return 0;
    }

    public int getEnergy() {
        return animal.getEnergy();
    }

    public int getEatenPlants() {
        return animal.getEatenPlants();
    }

    public int getChildrenCount() {
        return animal.getChildrenCount();
    }

    public int getOffspringCount() {
        return animal.getoffspringCount();
    }

    public int getAge() {
        return animal.getAge();
    }

    public String getDeathDay() {
        return animal.isDead() ? String.valueOf(simulation.getCurrentDay()) : "Alive";
}
}