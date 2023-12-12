package agh.ics.oop.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    public void canMoveToTest(){
        RectangularMap map = new RectangularMap(3,3);
        assertEquals(map.canMoveTo(new Vector2d(1,1)),true);
        assertEquals(map.canMoveTo(new Vector2d(3,3)),false);
        assertEquals(map.canMoveTo(new Vector2d(0,0)),true);
        assertEquals(map.canMoveTo(new Vector2d(4,4)),false);
        assertEquals(map.canMoveTo(new Vector2d(-1,-1)),false);
    }

    @Test
    public void placeTest() throws PositionAlreadyOccupiedException {
        RectangularMap map = new RectangularMap(3,3);
        Animal animal1 = new Animal(new Vector2d(1,1),MapDirection.NORTH);
        Animal animal2 = new Animal(new Vector2d(2,2),MapDirection.NORTH);
        map.place(animal1);
        map.place(animal2);
        assertThrowsExactly(PositionAlreadyOccupiedException.class, () -> map.place(animal1));
        assertThrowsExactly(PositionAlreadyOccupiedException.class, () -> map.place(animal2));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2d(1,1),MapDirection.NORTH)));
        assertEquals(map.objectAt(new Vector2d(1,1)),animal1);
        assertEquals(map.objectAt(new Vector2d(2,2)),animal2);
        assertNull(map.objectAt(new Vector2d(3, 3)));
    }

    @Test
    public void isOccupiedTest() throws PositionAlreadyOccupiedException {
        RectangularMap map = new RectangularMap(3,3);
        map.place(new Animal(new Vector2d(1,1),MapDirection.NORTH));
        assertEquals(map.isOccupied(new Vector2d(1,1)),true);
        assertEquals(map.isOccupied(new Vector2d(1,2)),false);
        assertEquals(map.isOccupied(new Vector2d(3,3)),false);
    }

    @Test
    public void objectAtTest() throws PositionAlreadyOccupiedException {
        RectangularMap map = new RectangularMap(4,4);
        Animal animal1 = new Animal(new Vector2d(1,0), MapDirection.NORTH);
        Animal animal2 = new Animal(new Vector2d(2,2), MapDirection.NORTH);
        map.place(animal1);
        map.place(animal2);
        assertEquals(map.objectAt(new Vector2d(1,0)),animal1);
        assertEquals(map.objectAt(new Vector2d(2,2)),animal2);
        assertEquals(map.objectAt(new Vector2d(3,3)),null);
    }

    @Test
    public void moveTest() throws PositionAlreadyOccupiedException {
        RectangularMap map = new RectangularMap(4, 4);
        Animal animal1 = new Animal(new Vector2d(1, 1),MapDirection.NORTH);
        Animal animal2 = new Animal(new Vector2d(2, 2),MapDirection.NORTH);
        map.place(animal1);
        map.place(animal2);
        map.move(animal1, MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(1, 2));
        assertEquals(animal1.getDirection(), MapDirection.NORTH);
        assertEquals(map.objectAt(new Vector2d(1, 2)), animal1);
        assertEquals(map.objectAt(new Vector2d(1, 1)), null);

        map.move(animal1, MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(1, 3));
        assertEquals(animal1.getDirection(), MapDirection.NORTH);
        assertEquals(map.objectAt(new Vector2d(1, 3)), animal1);
        assertEquals(map.objectAt(new Vector2d(1, 2)), null);

        map.move(animal1, MoveDirection.RIGHT);
        assertEquals(animal1.getPosition(), new Vector2d(1, 3));
        assertEquals(animal1.getDirection(), MapDirection.EAST);
        assertEquals(map.objectAt(new Vector2d(1, 3)), animal1);
        assertEquals(map.objectAt(new Vector2d(1, 2)), null);

        map.move(animal1, MoveDirection.LEFT);
        assertEquals(animal1.getPosition(), new Vector2d(1, 3));
        assertEquals(animal1.getDirection(), MapDirection.NORTH);
        assertEquals(map.objectAt(new Vector2d(1, 3)), animal1);
        assertEquals(map.objectAt(new Vector2d(1, 2)), null);

        map.move(animal2, MoveDirection.RIGHT);
        map.move(animal2, MoveDirection.BACKWARD);
        assertEquals(animal2.getPosition(), new Vector2d(1, 2));
        assertEquals(animal2.getDirection(), MapDirection.EAST);
        assertEquals(map.objectAt(new Vector2d(1, 2)), animal2);
        assertEquals(map.objectAt(new Vector2d(2, 2)), null);

        map.move(animal1, MoveDirection.BACKWARD);
        assertEquals(animal1.getPosition(), new Vector2d(1, 3));
        assertEquals(animal1.getDirection(), MapDirection.NORTH);
        assertEquals(map.objectAt(new Vector2d(1, 3)), animal1);
        assertEquals(map.objectAt(new Vector2d(1, 2)), animal2);

    }

}