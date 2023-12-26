package agh.ics.oop.model;

public interface WorldElement {
    boolean isAnimal();

    Animal asAnimal();

    String toString();
    Vector2d getPosition();
}
