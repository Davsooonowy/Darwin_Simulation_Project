package agh.ics.oop.model.util;

import agh.ics.oop.model.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MapVisualizer {
    private static final String EMPTY_CELL = " ";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = "|";
    private final WorldMap map;

    public MapVisualizer(WorldMap map) {
        this.map = map;
    }

    public String draw(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.getY() + 1; i >=lowerLeft.getY() - 1; i--) {
            if (i == upperRight.getY() + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.getX(); j <= upperRight.getX() + 1; j++) {
                if (i < lowerLeft.getY() || i > upperRight.getY()) {
                    builder.append(drawFrame(j <= upperRight.getX()));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.getX()) {
                        builder.append(drawObject(new Vector2d(j, i)));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.getX(); j < upperRight.getX() + 1; j++) {
            builder.append(String.format("%2d", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private Rectangle drawObject(Vector2d currentPosition) {
        if (this.map.isOccupied(currentPosition)) {
            WorldElement object = this.map.objectAt(currentPosition);
            if (object != null) {
                return object.toRectangle();
            }
        }
        return EMPTY_CELL;
    }
//
//    private Node drawObject(Vector2d currentPosition) {
//        if (this.map.isOccupied(currentPosition)) {
//            Object object = this.map.objectAt(currentPosition);
//            if (object instanceof Animal) {
//                return ((Animal) object);
//            } else if (object instanceof Grass) {
//                Rectangle rectangle = new Rectangle(10, 10);
//                rectangle.setFill(Color.GREEN);
//                return rectangle;
//            } else if (object instanceof Tunnel) {
//                Rectangle circle = new Rectangle(5,5);
//                circle.setFill(Color.BLACK);
//                return circle;
//            }
//        }
//        Rectangle rectangle = new Rectangle(10, 10);
//        rectangle.setFill(Color.BROWN);
//        return rectangle;
//    }
}