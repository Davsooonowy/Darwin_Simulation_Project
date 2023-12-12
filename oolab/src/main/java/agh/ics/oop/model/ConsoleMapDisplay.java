package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int changescnt;

    public ConsoleMapDisplay() {
        this.changescnt = 0;
    }
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        synchronized (System.out) {
            changescnt++;
            System.out.println("Id of an instance: " + worldMap.getId());
            System.out.println(message);
            System.out.println("Number of changes: " + changescnt);
            System.out.println(worldMap);
        }
    }
}