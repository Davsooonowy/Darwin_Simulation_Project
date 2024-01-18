package agh.ics.oop;
import agh.ics.oop.model.maps.Earth;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SimulationTest {
    @Test
    void reproducetest(){
        Simulation simulation = new Simulation(2, new Earth(1, 1, 10, 10, 10), 100, 5, 20, 20, 0, 0, "Complete predestination");
        simulation.groupAndReproduceAnimals();
        assertEquals(3, simulation.getAnimals().size());
        assertEquals(1, simulation.getAnimals().get(0).getChildrenCount());
        assertEquals(1, simulation.getAnimals().get(1).getChildrenCount());
        assertEquals(0, simulation.getAnimals().get(2).getChildrenCount());
        assertEquals(40, simulation.getAnimals().get(2).getEnergy());
    }

    @Test
    void eattest(){
        Simulation simulation = new Simulation(1, new Earth(1, 1, 10, 10, 10), 100, 5, 20, 20, 0, 0, "Complete predestination");
        simulation.eat();
        assertEquals(1, simulation.getAnimals().get(0).getEatenPlants());
        assertEquals(110, simulation.getAnimals().get(0).getEnergy());
    }

    @Test
    void removedeadbodiestest(){
        Simulation simulation = new Simulation(1, new Earth(1, 1, 10, 10, 10), 0, 5, 20, 20, 0, 0, "Complete predestination");
        simulation.removeDeadBodies();
        assertEquals(0, simulation.getAnimals().size());
        assertEquals(1, simulation.getDeadAnimals().size());
    }

    @Test
    void moveanimalstest(){
        Simulation simulation = new Simulation(1, new Earth(1, 1, 10, 10, 10), 10, 5, 20, 20, 0, 0, "Complete predestination");
        simulation.move_animals(0);
        assertEquals(9, simulation.getAnimals().get(0).getEnergy());
        assertEquals(1, simulation.getAnimals().get(0).getAge());
    }

    @Test
    void AnUnexpectedJourneyTest(){
        Simulation simulation = new Simulation(1, new Earth(5, 5, 10, 10, 10), 10, 5, 20, 20, 0, 0, "An Unexpected Journey");
        simulation.move_animals(0);
        int day0gene = simulation.getAnimals().get(0).getActiveGenome();
        simulation.move_animals(4);
        int day4gene = simulation.getAnimals().get(0).getActiveGenome();
        simulation.move_animals(5);
        int day5gene = simulation.getAnimals().get(0).getActiveGenome();
        simulation.move_animals(9);
        int day9gene = simulation.getAnimals().get(0).getActiveGenome();
        assertEquals(day0gene, day9gene);
        assertEquals(day4gene, day5gene);
    }

}
