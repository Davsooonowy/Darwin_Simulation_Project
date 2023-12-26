package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GrassGenerator implements Iterable<Vector2d> {
    protected final int maxWidth;
    protected final int maxHeight;
    protected final int maxPositions;

    public GrassGenerator(int maxWidth, int maxHeight, int maxPositions) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.maxPositions = Math.min(maxPositions, maxWidth * maxHeight);
    }

    protected List<Vector2d> generateRandomPositions() {
        List<Vector2d> allPositions = new ArrayList<>(maxWidth * maxHeight);
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                allPositions.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(allPositions);
        return allPositions.subList(0, maxPositions);
    }
}