package agh.ics.oop.model.maps;
import agh.ics.oop.model.mapObjects.Animal;
import agh.ics.oop.model.mapObjects.WorldElement;
import agh.ics.oop.model.vector_records.Boundary;
import agh.ics.oop.model.vector_records.Vector2d;

import java.util.Set;

public interface WorldMap {

    void place(Animal animal);

    void move(Animal animal, Integer direction);

    void eat(Animal animal);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);
    Set<WorldElement> getElements();
    Boundary getBounds();


}