package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapObjects.Animal;
import agh.ics.oop.model.vector_records.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AbstractWorldMapTest {

    @Test
    void placegrasstest(){
        Earth earth = new Earth(10, 10, 10, 10, 10);
        earth.placeGrass(10, earth.getGrassPositions());
        assertEquals(20, earth.getGrassPositions().size());
        earth.placeGrass(10, earth.getGrassPositions());
        assertEquals(30, earth.getGrassPositions().size());
        earth.placeGrass(1000, earth.getGrassPositions());
        assertEquals(100, earth.getGrassPositions().size());

        SecretTunnels secretTunnels = new SecretTunnels(10, 10, 10, 10, 10);
        secretTunnels.placeGrass(10, secretTunnels.getGrassPositions());
        assertEquals(20, secretTunnels.getGrassPositions().size());
        secretTunnels.placeGrass(10, secretTunnels.getGrassPositions());
        assertEquals(30, secretTunnels.getGrassPositions().size());
        secretTunnels.placeGrass(1000, secretTunnels.getGrassPositions());
        assertEquals(100, secretTunnels.getGrassPositions().size());
    }

    @Test
    void getfreefieldstest(){
        Animal animal = new Animal(new Vector2d(0,0), 10, 10, 10, 10, 10, 10);
        Earth earth = new Earth(10, 10, 10, 10, 10);
        earth.place(animal);
        assertEquals(99, earth.getFreeFields());
    }

}
