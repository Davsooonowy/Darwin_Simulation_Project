package agh.ics.oop.model;

import javafx.scene.shape.Rectangle;

public interface WorldElement {

    Rectangle toRectangle();
    Vector2d getPosition();
}
