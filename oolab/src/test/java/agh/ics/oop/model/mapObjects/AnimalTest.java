package agh.ics.oop.model.mapObjects;

import agh.ics.oop.model.mapObjects.properties.MapDirection;
import agh.ics.oop.model.maps.Earth;
import agh.ics.oop.model.maps.SecretTunnels;
import agh.ics.oop.model.vector_records.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AnimalTest {

    Earth earth = new Earth(10, 10, 10, 10, 10);
    SecretTunnels secretTunnels = new SecretTunnels(10, 10, 10, 10, 10);
    //rozmnażanie i potomstwo
    @Test
    void reproducetest(){
        Animal animal1 = new Animal(new Vector2d(2,2), 10, 32, 10, 10, 1, 1);
        Animal animal2 = new Animal(new Vector2d(2,2), 10, 32, 10, 10, 1, 1);
        Animal child = animal1.reproduce(animal2);
        assertEquals(0, animal1.getEnergy());
        assertEquals(0, animal2.getEnergy());
        assertEquals(20, child.getEnergy());
        assertEquals(1, animal1.getChildrenCount());
        assertEquals(1, animal2.getChildrenCount());
        assertEquals(new Vector2d(2,2), child.position());
    }

    @Test
    void moveTest() {
        //Poruszanie się na mapie
        Animal animal1 = new Animal(new Vector2d(2, 2), 10, 32, 10, 10, 1, 1);
        animal1.setDirection(MapDirection.NORTH);
        animal1.move(0, earth);
        assertEquals(new Vector2d(2, 3), animal1.position());
        animal1.move(1, earth);
        assertEquals(new Vector2d(3, 4), animal1.position());
        animal1.move(2, earth);
        assertEquals(new Vector2d(4, 3), animal1.position());
        animal1.move(3, earth);
        assertEquals(new Vector2d(3, 3), animal1.position());
        animal1.move(4, earth);
        assertEquals(new Vector2d(4, 3), animal1.position());
        animal1.move(5, earth);
        assertEquals(new Vector2d(3, 4), animal1.position());
        animal1.move(6, earth);
        assertEquals(new Vector2d(2, 3), animal1.position());
        animal1.move(7, earth);
        assertEquals(new Vector2d(2, 2), animal1.position());

        //Krawędź górna/dolna
        Animal animal2 = new Animal(new Vector2d(2,9), 10, 32, 10, 10, 1, 1);
        animal2.setDirection(MapDirection.NORTHEAST);
        animal2.move(0, earth);
        assertEquals(new Vector2d(2, 9), animal2.position());
        assertEquals(MapDirection.SOUTHWEST, animal2.getDirection());
        animal2.move(0, earth);
        assertEquals(new Vector2d(1, 8), animal2.position());
        assertEquals(MapDirection.SOUTHWEST, animal2.getDirection());

        //Krawędź boczna
        Animal animal3 = new Animal(new Vector2d(9,5), 10, 32, 10, 10, 1, 1);
        animal3.setDirection(MapDirection.EAST);
        animal3.move(0, earth);
        assertEquals(new Vector2d(0, 5), animal3.position());
        animal3.move(4,earth);
        assertEquals(new Vector2d(9, 5), animal3.position());
    }





}
