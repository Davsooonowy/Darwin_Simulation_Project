package agh.ics.oop.model;

import java.util.*;

public class GrassGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int maxPositions;
    private final Set<Vector2d> grassPositions;

    public GrassGenerator(int maxWidth, int maxHeight, int maxPositions, Set<Vector2d> grassPositions) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.maxPositions = Math.min(maxPositions, maxWidth * maxHeight - grassPositions.size());
        this.grassPositions = grassPositions;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            private final List<Vector2d> positions;

            {
                positions = generateRandomPositions();
            }

            private List<Vector2d> generateRandomPositions() {
                List<Vector2d> preferredPositions = new ArrayList<>();
                List<Vector2d> nonPreferredPositions = new ArrayList<>();
                Random random = new Random();

                int equatorStart = 2 * maxHeight / 5;
                int equatorEnd = 3 * maxHeight / 5;

                for (int x = 0; x < maxWidth; x++) {
                    for (int y = 0; y < maxHeight; y++) {
                        Vector2d position = new Vector2d(x, y);
                        if (!grassPositions.contains(position)) {
                            if (y >= equatorStart && y < equatorEnd) {
                                preferredPositions.add(position);
                            } else {
                                nonPreferredPositions.add(position);
                            }
                        }
                    }
                }

                List<Vector2d> allPositions = new ArrayList<>(maxPositions);
                while (allPositions.size() < maxPositions && !(grassPositions.size() >= maxWidth * maxHeight)) {
                    if (random.nextDouble() <= 0.8 && !preferredPositions.isEmpty()) {
                        allPositions.add(preferredPositions.remove(random.nextInt(preferredPositions.size())));
                    } else if (!nonPreferredPositions.isEmpty()) {
                        allPositions.add(nonPreferredPositions.remove(random.nextInt(nonPreferredPositions.size())));
                    }
                }

                return allPositions;
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