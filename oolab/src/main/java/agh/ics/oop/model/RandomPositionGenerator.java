package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int maxPositions;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int maxPositions) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.maxPositions = Math.min(maxPositions, maxWidth * maxHeight);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            private final List<Vector2d> positions;

            {
                positions = generateRandomPositions();
            }

            private List<Vector2d> generateRandomPositions() {
                List<Vector2d> allPositions = new ArrayList<>(maxWidth * maxHeight);
                for (int x = 0; x < maxWidth; x++) {
                    for (int y = 0; y < maxHeight; y++) {
                        allPositions.add(new Vector2d(x, y));
                    }
                }

                Collections.shuffle(allPositions);
                return allPositions.subList(0, maxPositions);
            }

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
}
