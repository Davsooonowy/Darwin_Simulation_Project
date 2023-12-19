package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.UUID;

public abstract class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected MapVisualizer visualizer;
    protected ArrayList<MapChangeListener> mapChangeListeners;
    protected final int height;
    protected final int width;
    protected final Vector2d lowerLeft;
    protected final Vector2d upperRight;

    public Set<WorldElement> getElements() {
        return new HashSet<>(animals.values());
    }

    public AbstractWorldMap(int width, int height) {
        this.height = height;
        this.width = width;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        visualizer = new MapVisualizer(this);
        mapChangeListeners = new ArrayList<>();
    }

    public synchronized String toString() {
        Boundary boundaries = getCurrentBounds();
        return visualizer.draw(boundaries.lowerLeft(), boundaries.upperRight());
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

    private synchronized void mapChanged() {
        for (MapChangeListener listener : mapChangeListeners) {
            listener.mapChanged(this);
        }
    }
}
