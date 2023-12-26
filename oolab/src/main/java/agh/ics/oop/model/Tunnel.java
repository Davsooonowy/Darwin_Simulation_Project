package agh.ics.oop.model;

public class Tunnel implements WorldElement{
    private final Vector2d position;
    private final Tunnel connectingTunnel;

    public Tunnel(Vector2d position, Tunnel connectingTunnel) {
        this.position = position;
        this.connectingTunnel = connectingTunnel;
    }

    public Vector2d getPosition() {
        return position;

    }
    public String toString(){
        return "[]";
    }
}

