package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;

public class OptionsParser {

    public static List<Integer> parse(String[] args) {
        List<Integer> directions = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "0":
                    directions.add(0);
                    break;
                case "1":
                    directions.add(1);
                    break;
                case "2":
                    directions.add(2);
                    break;
                case "3":
                    directions.add(3);
                    break;
                case "4":
                    directions.add(4);
                    break;
                case "5":
                    directions.add(5);
                    break;
                case "6":
                    directions.add(6);
                    break;
                case "7":
                    directions.add(7);
                    break;
                default:
                    throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }
        return directions;
    }
}