package agh.ics.oop.model;


import agh.ics.oop.model.*;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    public final HashMap<Vector2d, Grass> grasses = new HashMap<>();
    protected ArrayList<MapChangeListener> mapChangeListeners;
    protected final int height;
    protected final int width;
    protected final Vector2d lowerLeft;
    protected final Vector2d upperRight;
    protected final int plantEnergy;
    protected final int plantSpawnRate;

    public Set<WorldElement> getElements() {
        return new HashSet<>(animals.values());
    }

    public Set<Vector2d> getGrassPositions() {
        return grasses.keySet();
    }

    public int getPlantSpawnRate(){
        return plantSpawnRate;
    }

    public AbstractWorldMap(int width, int height,int plantEnergy,int initialGrassQuantity, int plantSpawnRate) {
        this.height = height;
        this.width = width;
        this.plantEnergy=plantEnergy;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        mapChangeListeners = new ArrayList<>();
        this.plantSpawnRate = plantSpawnRate;
        placeGrass(initialGrassQuantity,getGrassPositions());
    }

    public void placeGrass(int grassQuantity, Set<Vector2d> grassPositions) {
        GrassGenerator grassGenerator = new GrassGenerator(width, height, grassQuantity, grassPositions);
        for (Vector2d grassPosition : grassGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
        mapChanged();
    }

    @Override
    public void place(Animal animal){
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            mapChanged();
        }
    }

    @Override
    public void move(Animal animal, Integer direction) {
        animals.remove(animal.getPosition());
        animal.move(direction, this);
        animals.put(animal.getPosition(), animal);
        mapChanged();
    }

    public void removeDeadAnimal(Animal animal){
        animals.remove(animal.getPosition());
        mapChanged();
    }

    @Override
    public void eat(Animal animal){
        animal.animalEnergyChange(plantEnergy);
        grasses.remove(animal.getPosition());
        mapChanged();
    }
    public List<Animal> getAnimalsOnField(Vector2d position) {
        List<Animal> animalsOnField = new ArrayList<>();
        for (Animal animal : animals.values()) {
            if (animal.getPosition().equals(position)) {
                animalsOnField.add(animal);
            }
        }
        return animalsOnField;
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

    public WorldElement grassAt(Vector2d position){
        return grasses.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(animals.containsKey(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }


    public void addMapChangeListener(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public void removeMapChangeListener(MapChangeListener listener) {
        mapChangeListeners.remove(listener);
    }

    public abstract Boundary getCurrentBounds();

    public Boolean horizontaledge(Vector2d position) {
        return position.getY() <= upperRight.getY() && position.getY() >= lowerLeft.getY();
    }

    public Boolean verticaledge(Vector2d position) {
        return position.getX() >= lowerLeft.getX() && position.getX() <= upperRight.getX();
    }

    public synchronized void mapChanged() {
        for (MapChangeListener listener : mapChangeListeners) {
            listener.mapChanged(this);
        }
    }
}