package agh.ics.oop.model;

public class Tunnel implements WorldElement{
    private final Vector2d position;
    private final Vector2d connected;

    public Tunnel(Vector2d position, Vector2d connected) {
        this.position = position;
        this.connected = connected;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getConnected() {
        return connected;
    }


    public String toString(){
        return "[]";
    }
}

