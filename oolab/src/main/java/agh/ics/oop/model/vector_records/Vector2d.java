package agh.ics.oop.model.vector_records;

public record Vector2d(int x, int y) {

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2d that)) {
            return false;
        }
        return this.x == that.x && this.y == that.y;
    }
}
