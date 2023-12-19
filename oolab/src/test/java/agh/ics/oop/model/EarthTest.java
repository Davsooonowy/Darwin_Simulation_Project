package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class EarthTest {
    @Test
    void plantGrassTest() {
        Earth map = new Earth(10, 10, 10);
        assertEquals(10, map.getGrassSize());
    }
    @Test
    public void isOccupiedTest() throws PositionAlreadyOccupiedException {
        Earth map = new Earth(10, 10, 10);
        map.place(new Animal(new Vector2d(1,1), 0));
        assertThrowsExactly(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2d(1,1),0)));
    }
}
