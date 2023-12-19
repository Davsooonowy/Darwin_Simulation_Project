package agh.ics.oop.model;

import java.util.Set;
import java.util.UUID;

public interface WorldMap extends MoveValidator {

    void place(Animal animal);

    void move(Animal animal, Integer direction);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);
    Set<WorldElement> getElements();

    Boundary getCurrentBounds();
}