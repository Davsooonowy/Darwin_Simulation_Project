package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Earth extends AbstractWorldMap {

    private final HashMap<Vector2d, Grass> grasses = new HashMap<>();
    private final HashMap<Vector2d, Vector2d> tunnels = new HashMap<>();
    private List<WorldElement> elements = new ArrayList<>();


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
        public List<WorldElement> objectsAt(Vector2d position) {
        return elements.stream()
            .filter(element -> element.getPosition().equals(position))
            .collect(Collectors.toList());
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
