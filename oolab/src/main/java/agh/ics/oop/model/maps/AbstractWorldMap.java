package agh.ics.oop.model.maps;

import agh.ics.oop.model.generators.GrassGenerator;
import agh.ics.oop.model.mapObjects.Animal;
import agh.ics.oop.model.mapObjects.Grass;
import agh.ics.oop.model.mapObjects.WorldElement;
import agh.ics.oop.model.vector_records.Boundary;
import agh.ics.oop.model.vector_records.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {

    ///                                                   fields                                                 ///
    protected HashMap<Vector2d, Animal> animals = new HashMap<>();
    public final HashMap<Vector2d, Grass> grasses = new HashMap<>(); // public?
    protected ArrayList<MapChangeListener> mapChangeListeners;
    protected  final Boundary boundary;
    public final int height;
    public final int width;
    protected final Vector2d lowerLeft;
    protected final Vector2d upperRight;
    protected final int plantEnergy;
    protected final int plantSpawnRate;


    ///                                                constructor                                          ///

    public AbstractWorldMap(int width, int height,int plantEnergy,int initialGrassQuantity, int plantSpawnRate) {
        this.height = height;
        this.width = width;
        this.plantEnergy=plantEnergy;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        mapChangeListeners = new ArrayList<>();
        this.plantSpawnRate = plantSpawnRate;
        this.boundary = new Boundary(lowerLeft, upperRight);
        placeGrass(initialGrassQuantity,getGrassPositions());
    }

    ///                                                getters                                                 ///
    public Set<WorldElement> getElements() {
        return new HashSet<>(animals.values());
    }

    public Set<Vector2d> getGrassPositions() {
        return grasses.keySet();
    }

    public int getPlantSpawnRate(){
        return plantSpawnRate;
    }

    public List<Animal> getAnimalsOnField(Vector2d position) {
        List<Animal> animalsOnField = new ArrayList<>();
        for (Animal animal : animals.values()) {
            if (animal.position().equals(position)) {
                animalsOnField.add(animal);
            }
        }
        return animalsOnField;
    }

    public Boundary getBounds() {
        return this.boundary;
    }

    public int getFreeFields() {
        List<Vector2d> occupiedFields = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vector2d position = new Vector2d(x, y);
                if (objectAt(position) instanceof Animal) {
                    occupiedFields.add(position);
                }
            }
        }
        return width * height - occupiedFields.size();
    }

    public Vector2d getMostPreferredPosition() {
        int equatorStart = 2 * height / 5;
        int equatorEnd = 3 * width / 5;

        Map<Vector2d, Long> grassPositionsInPreferredArea = grasses.keySet().stream()
                .filter(position -> position.y() >= equatorStart && position.y() < equatorEnd)
                .collect(Collectors.groupingBy(position -> position, Collectors.counting()));

        return grassPositionsInPreferredArea.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    ///                                                grass                                                    ///

    public void placeGrass(int grassQuantity, Set<Vector2d> grassPositions) {
        GrassGenerator grassGenerator = new GrassGenerator(width, height, grassQuantity, grassPositions);
        for (Vector2d grassPosition : grassGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
        mapChanged();
    }

    public WorldElement grassAt(Vector2d position){
        return grasses.get(position);
    }

    ///                                                operations on animals                          ///
    @Override
    public void place(Animal animal){
            animals.put(animal.position(), animal);
            mapChanged();
    }

    @Override
    public void move(Animal animal, Integer direction) {
        animals.remove(animal.position());
        animal.move(direction, this);
        animals.put(animal.position(), animal);
        mapChanged();
    }

    public void removeDeadAnimal(Animal animal){
        animals.remove(animal.position());
        mapChanged();
    }

    @Override
    public void eat(Animal animal){
        animal.animalEnergyChange(plantEnergy);
        grasses.remove(animal.position());
        mapChanged();
    }

    public void sortAnimals(List<Animal> animalsOnField) {
        animalsOnField.sort((a1, a2) -> {
            int energyComparison = Integer.compare(a2.getEnergy(), a1.getEnergy());
            if (energyComparison != 0) return energyComparison;

            int ageComparison = Integer.compare(a2.getAge(), a1.getAge());
            if (ageComparison != 0) return ageComparison;

            return Integer.compare(a2.getChildrenCount(), a1.getChildrenCount());
        });
    }

    public Animal chooseTopAnimal(List<Animal> animalsOnField) {
        List<Animal> topAnimals = new ArrayList<>();
        Animal topAnimal = animalsOnField.get(0);
        topAnimals.add(topAnimal);

        for (int i = 1; i < animalsOnField.size(); i++) {
            Animal animal = animalsOnField.get(i);
            if (animal.getEnergy() == topAnimal.getEnergy() &&
                    animal.getAge() == topAnimal.getAge() &&
                    animal.getChildrenCount() == topAnimal.getChildrenCount()) {
                topAnimals.add(animal);
            } else {
                break;
            }
        }

        Random random = new Random();
        return topAnimals.get(random.nextInt(topAnimals.size()));
    }

    public Animal chooseAnimal(Vector2d position) {
        List<Animal> animalsOnField = getAnimalsOnField(position);

        if (animalsOnField.isEmpty()) {
            return null;
        }

        sortAnimals(animalsOnField);

        return chooseTopAnimal(animalsOnField);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    public void addMapChangeListener(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public Boolean horizontaledge(Vector2d position) { // nazwa
        return position.y() <= upperRight.y() && position.y() >= lowerLeft.y();
    }

    public Boolean verticaledge(Vector2d position) {
        return position.x() >= lowerLeft.x() && position.x() <= upperRight.x();
    }

    public synchronized void mapChanged() { // public?
        for (MapChangeListener listener : mapChangeListeners) {
            listener.mapChanged(this);
        }
    }
}