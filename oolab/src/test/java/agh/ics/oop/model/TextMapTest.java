//package agh.ics.oop.model;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TextMapTest {
//
//    @Test
//    void placeTest() {
//        List<String> args = new ArrayList<>();
//        args.add("ala");
//        args.add("ma");
//        args.add("kota");
//        TextMap map = new TextMap();
//        assertTrue(map.place("a"));
//    }
//
//    @Test
//    void moveTest() {
//        WorldMap<String, Integer> map = new TextMap();
//        map.place("ala");
//        map.place("ma");
//        map.place("kota");
//        map.move("ala", MoveDirection.FORWARD);
//        assertEquals(map.toString(), "ma ala kota ");
//        map.move("kota", MoveDirection.BACKWARD);
//        assertEquals(map.toString(), "ma kota ala ");
//        map.move("ala", MoveDirection.FORWARD);
//        assertEquals(map.toString(), "ma kota ala ");
//        map.move("ala", MoveDirection.BACKWARD);
//        assertEquals(map.toString(), "ma ala kota ");
//        map.move("ala", MoveDirection.BACKWARD);
//        assertEquals(map.toString(), "ala ma kota ");
//    }
//
//    @Test
//    void objectAt() {
//        WorldMap<String, Integer> map = new TextMap();
//        map.place("ala");
//        map.place("ma");
//        map.place("kota");
//        assertEquals(map.objectAt(0), "ala");
//        assertEquals(map.objectAt(1), "ma");
//        assertEquals(map.objectAt(2), "kota");
//        assertNull(map.objectAt(3));
//    }
//
//    @Test
//    void toStringTest() {
//        WorldMap<String, Integer> map = new TextMap();
//        map.place("ala");
//        map.place("ma");
//        map.place("kota");
//        assertEquals(map.toString(), "ala ma kota ");
//    }
//}