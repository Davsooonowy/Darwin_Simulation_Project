package agh.ics.oop.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    Vector2d v = new Vector2d(4,1);
    Vector2d u = new Vector2d(-1,2);
    Vector2d w = new Vector2d(5,2);
    @Test
    void testToString() {
        assertEquals("(4,1)",v.toString());
        assertEquals("(-1,2)",u.toString());
        assertEquals("(5,2)",w.toString());
    }

    @Test
    void precedes() {
        assertFalse(v.precedes(u));
        assertFalse(w.precedes(v));
        assertTrue(v.precedes(w));
        assertTrue(u.precedes(w));
    }

    @Test
    void follows() {
        assertFalse(v.follows(u));
        assertTrue(w.follows(v));
        assertTrue(w.follows(u));
    }

    @Test
    void upperRight() {
        assertEquals(new Vector2d(4,2), v.upperRight(u));
        assertEquals(new Vector2d(5,2), v.upperRight(w));
        assertEquals(new Vector2d(5,2), w.upperRight(u));
    }

    @Test
    void lowerLeft() {
        assertEquals(new Vector2d(-1,1), v.lowerLeft(u));
        assertEquals(new Vector2d(4,1), v.lowerLeft(w));
        assertEquals(new Vector2d(-1,2), w.lowerLeft(u));
    }

    @Test
    void add() {
        assertEquals(new Vector2d(3,3), v.add(u));
        assertEquals(new Vector2d(9,3), v.add(w));
        assertEquals(new Vector2d(4,4), w.add(u));
    }

    @Test
    void subtract() {
        assertEquals(new Vector2d(5,-1), v.subtract(u));
        assertEquals(new Vector2d(-1,-1), v.subtract(w));
        assertEquals(new Vector2d(-6,0), u.subtract(w));
    }

    @Test
    void testEquals() {
        assertNotEquals(v, u);
        assertNotEquals(v, w);
        assertNotEquals(u, w);
        assertEquals(v, v);
    }

    @Test
    void opposite() {
        assertEquals(new Vector2d(-4,-1), v.opposite());
        assertEquals(new Vector2d(1,-2), u.opposite());
        assertEquals(new Vector2d(-5,-2), w.opposite());
    }
}