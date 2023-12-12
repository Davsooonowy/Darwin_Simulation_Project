package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    MoveValidator validator = new RectangularMap(4, 4);
    @Test

    void orientation(){ //only orientation test
        Animal animal = new Animal();
        assertEquals(MapDirection.NORTH,animal.getDirection());
        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.EAST,animal.getDirection());
        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.SOUTH,animal.getDirection());
        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.WEST,animal.getDirection());
        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.NORTH,animal.getDirection());
        animal.move(MoveDirection.LEFT, validator);
        assertEquals(MapDirection.WEST,animal.getDirection());
        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.LEFT, validator);
        assertEquals(MapDirection.NORTH,animal.getDirection());
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.RIGHT,validator);
        assertEquals(MapDirection.WEST,animal.getDirection());
    }

    @Test void move_test(){ //only move test and check if an animal does not go beyond the map
        Animal animal = new Animal();
        assertTrue(animal.isAt(new Vector2d(2,2)));
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,3)));
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,3)));
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,2)));
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,0)));
        animal.move(MoveDirection.RIGHT, validator); //i have to turn the animal to check x-direction
        assertEquals(MapDirection.EAST,animal.getDirection()); //control position check
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(3,0)));
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertFalse(animal.isAt(new Vector2d(4,0)));
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        assertTrue(animal.isAt(new Vector2d(0,0)));
    }

    @Test
    void overall_test(){ //overall test of animal movement
        Animal animal = new Animal();
        assertTrue(animal.isAt(new Vector2d(2,2)));

        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.EAST,animal.getDirection());

        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.LEFT, validator);
        assertEquals(MapDirection.NORTH,animal.getDirection());

        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,3)));

        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,3)));

        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,3)));

        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(3,3)));

        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.RIGHT, validator);
        assertEquals(MapDirection.EAST,animal.getDirection());
        assertTrue(animal.isAt(new Vector2d(3,3)));

        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(MapDirection.NORTH,animal.getDirection());
        assertTrue(animal.isAt(new Vector2d(3,3)));

        animal.move(MoveDirection.BACKWARD, validator);
        assertTrue(animal.isAt(new Vector2d(3,2)));

        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        assertTrue(animal.isAt(new Vector2d(3,0)));

        animal.move(MoveDirection.BACKWARD, validator);
        assertTrue(animal.isAt(new Vector2d(3,0)));

        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        assertEquals(MapDirection.NORTH,animal.getDirection());
        assertTrue(animal.isAt(new Vector2d(3,0)));

        animal.move(MoveDirection.LEFT, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,0)));

        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(1,0)));

        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(0,0)));

        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(0,0)));

        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(MapDirection.WEST,animal.getDirection());

        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        assertTrue(animal.isAt(new Vector2d(2,0)));
        assertTrue(animal.isAt(new Vector2d(2,0)));

        animal.move(MoveDirection.FORWARD, validator);
        assertEquals(MapDirection.WEST,animal.getDirection());

        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(0,0)));

        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.BACKWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertTrue(animal.isAt(new Vector2d(3,1)));
        assertEquals(MapDirection.EAST,animal.getDirection());

        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.FORWARD, validator);
        assertFalse(animal.isAt(new Vector2d(2,1)));
        assertEquals(MapDirection.WEST,animal.getDirection());
    }
}