package agh.ics.oop.Statistics;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.mapObjects.Animal;


//TODO: -czyszczenie kodu (clean code) Dawid
//TODO: -optymalizacja both
//TODO: -podświetlanie zwierzaków i trawy na zawołanie na koniec przed oddaniem xD
//TODO: -testy Maciej W
//TODO: walidacja danych, errory
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

    public int getActivePart() {
        return animal.getActiveGenome();
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
        return animal.isDead() ? String.valueOf(animal.getDeathDay()) : "Alive";
}
}