package agh.ics.oop.model;
import java.util.Set;

public interface WorldMap extends MoveValidator {

    void place(Animal animal);

    void move(Animal animal, Integer direction);

    void eat(Animal animal);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);
    Set<WorldElement> getElements();
    Boundary getCurrentBounds();


}