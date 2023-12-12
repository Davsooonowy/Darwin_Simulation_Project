package agh.ics.oop.model;
import agh.ics.oop.OptionsParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OptionsParserTest {
    @Test
    void parse_OK() {
        String[] args = {"f", "b", "r", "l"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<MoveDirection> expectedDirections = new ArrayList<MoveDirection>() {
            {
                add(MoveDirection.FORWARD);
                add(MoveDirection.BACKWARD);
                add(MoveDirection.RIGHT);
                add(MoveDirection.LEFT);
            }
        };

        assertEquals(expectedDirections, directions);
    }

    @Test
    void parse_NOT_OK() {
        String[] args = {"f","z", "b", "r", "l"};
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(args));
    }
}

