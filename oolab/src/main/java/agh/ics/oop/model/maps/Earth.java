package agh.ics.oop.model.maps;

import agh.ics.oop.model.vector_records.Vector2d;
import agh.ics.oop.model.mapObjects.WorldElement;

import java.util.Set;

public class Earth extends AbstractWorldMap {

    public Earth(int height, int width, int plantEnergy, int initialGrassQuantity, int plantspawnRate) {
        super(width,height,plantEnergy,initialGrassQuantity,plantspawnRate);

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

    int getGrassSize() {
        return grasses.size();
    }
}
