package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.UUID;

public abstract class AbstractWorldMap implements WorldMap{
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected MapVisualizer visualizer;
    protected ArrayList<MapChangeListener> mapChangeListeners;
    protected ArrayList<MapChangeListener> listeners;
    protected UUID id;

    public Set<WorldElement> getElements() {
        return new HashSet<>(animals.values());
    }

    public AbstractWorldMap() {
        visualizer = new MapVisualizer(this);
        listeners = new ArrayList<>();
        id = UUID.randomUUID();
        mapChangeListeners = new ArrayList<>();
    }

    public synchronized String toString() {
        Boundary boundaries = getCurrentBounds();
        return visualizer.draw(boundaries.lowerLeft(), boundaries.upperRight());
    }
    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            mapChanged("New animal " + animal + " placed on the map at " + animal.getPosition().toString());
        }else {
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        MapDirection oldDirection =animal.getDirection();
        animals.remove(animal.getPosition());
        animal.move(direction, this);
        animals.put(animal.getPosition(), animal);
        if(!oldPosition.equals(animal.getPosition())) {
            mapChanged("Animal " + animal + " moved from " + oldPosition + " to " + animal.getPosition().toString());
        } else if (oldPosition.equals(animal.getPosition()) && !oldDirection.equals(animal.getDirection())) {
            mapChanged("Animal " + animal + " changed orientation from: " + oldDirection + " to " + animal.getDirection().toString());
        } else {
            System.out.println("Animal " + animal + " did not move\n");
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(animals.containsKey(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    public void addListener(MapChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MapChangeListener listener) {
        listeners.remove(listener);
    }
    public void addMapChangeListener(MapChangeListener listener){
        mapChangeListeners.add(listener);
    }
    public void removeMapChangeListener(MapChangeListener listener){
        mapChangeListeners.remove(listener);
    }

    private synchronized void mapChanged(String result) {
        for (MapChangeListener listener : mapChangeListeners) {
            listener.mapChanged(this, result);
        }
    }
    public UUID getId() {
        return id;
    }
    public abstract Boundary getCurrentBounds();
}