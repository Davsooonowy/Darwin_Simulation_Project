package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Set;

public class GrassField extends AbstractWorldMap {
    private final int grassUpperRange;
    private HashMap<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassQauntity) {
        super();
        grassUpperRange = (int) Math.sqrt(grassQauntity * 10);

        placeGrass(grassQauntity);
    }

    void placeGrass(int grassQuantity) {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(grassUpperRange, grassUpperRange, grassQuantity);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }
    @Override
    public WorldElement objectAt(Vector2d position) {
        if(super.isOccupied(position)){
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
        Vector2d lowerleft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Vector2d position : this.animals.keySet()) {
            lowerleft = lowerleft.lowerLeft(position);
        }

        for (Vector2d position : this.grasses.keySet()) {
            lowerleft = lowerleft.lowerLeft(position);
        }

        return lowerleft;
    }

    public Vector2d checkUpperRight() {
        Vector2d upperright = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Vector2d position : this.animals.keySet()) {
            upperright = upperright.upperRight(position);
        }

        for (Vector2d position : this.grasses.keySet()) {
            upperright = upperright.upperRight(position);
        }

        return upperright;
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

    int getGrassSize(){
        return grasses.size();
    }
}