package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Set;

public class Earth extends AbstractWorldMap {
    private final int height;
    private final int width;
    private HashMap<Vector2d, Grass> grasses = new HashMap<>();

    public Earth(int height, int width, int initialGrassQuantity) {
        super();
        this.height = height;
        this.width = width;
        placeGrass(initialGrassQuantity);
    }

    void placeGrass(int grassQuantity) {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grassQuantity);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.isOccupied(position)) {
            return super.objectAt(position);
        } else {
            return grasses.get(position);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grasses.containsKey(position);
    }

    public Vector2d checkLowerLeft() {
        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Vector2d position : this.animals.keySet()) {
            lowerLeft = lowerLeft.lowerLeft(position);
        }

        for (Vector2d position : this.grasses.keySet()) {
            lowerLeft = lowerLeft.lowerLeft(position);
        }

        return lowerLeft;
    }

    public Vector2d checkUpperRight() {
        Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Vector2d position : this.animals.keySet()) {
            upperRight = upperRight.upperRight(position);
        }

        for (Vector2d position : this.grasses.keySet()) {
            upperRight = upperRight.upperRight(position);
        }

        return upperRight;
    }

    @Override
    public Set<WorldElement> getElements() {
        Set<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(checkLowerLeft(), checkUpperRight());
    }

    int getGrassSize() {
        return grasses.size();
    }
}
