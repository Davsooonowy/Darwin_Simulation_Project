package agh.ics.oop.model.mapObjects;

import agh.ics.oop.model.vector_records.Vector2d;

public class Grass implements WorldElement {
    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d position() {
        return position;

    }
}
