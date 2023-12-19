package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Set;

public class Earth extends AbstractWorldMap {

    private final HashMap<Vector2d, Grass> grasses = new HashMap<>();

    public Earth(int height, int width, int initialGrassQuantity) {
        super(width,height);

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


    @Override
    public Set<WorldElement> getElements() {
        Set<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }


    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(lowerLeft, upperRight);
    }

    int getGrassSize() {
        return grasses.size();
    }
}
