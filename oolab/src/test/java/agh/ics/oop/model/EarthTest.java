package agh.ics.oop.model;

import agh.ics.oop.model.maps.Earth;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class EarthTest {
    @Test
    void plantGrassTest() {
        Earth map = new Earth(10, 10, 10);
        assertEquals(10, map.getGrassSize());
    }
}
