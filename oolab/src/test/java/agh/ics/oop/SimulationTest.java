package agh.ics.oop;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationTest {
    @Test
    void test() {
        GrassField map1 = new GrassField(10);
        List<MoveDirection> directions1 = OptionsParser.parse(new String[]{"f", "f", "r", "l"});
        List<Vector2d> positions1 = List.of(new Vector2d(1, 2), new Vector2d(4, 4));
        Simulation simulation1 = new Simulation(directions1, positions1, map1);
        simulation1.run();

        assertTrue(simulation1.getAnimal(0).isAt(new Vector2d(1, 3)));

        GrassField map2 = new GrassField(10);
        List<MoveDirection> directions2 = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        List<Vector2d> positions2 = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        Simulation simulation2 = new Simulation(directions2, positions2, map2);
        simulation2.run();

        assertTrue(simulation2.getAnimal(0).isAt(new Vector2d(2, -1)));
        assertTrue(simulation2.getAnimal(1).isAt(new Vector2d(3, 7)));
    }
}