package agh.ics.oop.model.mapObjects;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldElement;

public record Tunnel(Vector2d position, Vector2d connected) implements WorldElement {
}

