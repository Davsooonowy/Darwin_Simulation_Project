package agh.ics.oop.model;

public class Tunnel implements WorldElement{
    private final Vector2d position;

    public Tunnel(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;

    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    @Override
    public Animal asAnimal() {
        return null;
    }

    public String toString(){
        return "[]";
    }
}

