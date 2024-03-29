package agh.ics.oop.model.mapObjects;

import agh.ics.oop.model.vector_records.Vector2d;

public record Tunnel(Vector2d position, Vector2d connected) implements WorldElement {
}

