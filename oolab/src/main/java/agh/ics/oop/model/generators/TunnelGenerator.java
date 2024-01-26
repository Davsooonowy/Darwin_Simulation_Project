package agh.ics.oop.model.generators;

import agh.ics.oop.model.vector_records.Vector2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TunnelGenerator extends AbstractGenerator {
    private final List<List<Vector2d>> positionTuples = new ArrayList<>();

    public TunnelGenerator(int maxWidth, int maxHeight, int maxPositions) {
        super(maxWidth, maxHeight, maxPositions);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            private final List<Vector2d> positions = generateRandomPositions();
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < maxPositions;
            }

            @Override
            public Vector2d next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more positions to generate");
                }

                Vector2d position = positions.get(currentIndex);
                currentIndex++;

                return position;
            }
        };
    }

    public List<Vector2d> generateRandomPositions() {
        List<Vector2d> allPositions = super.generateRandomPositions();
        List<Vector2d> subList = allPositions.subList(0, maxPositions);

        for (int i = 0; i < subList.size() - 1; i += 2) {
            List<Vector2d> tuple = new ArrayList<>();
            tuple.add(subList.get(i));
            tuple.add(subList.get(i + 1));
            positionTuples.add(tuple);
        }

        return subList;
    }

    public List<List<Vector2d>> getPositionTuples() {
        return positionTuples; // dehermetyzacja
    }
}