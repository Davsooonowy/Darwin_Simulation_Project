package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class SecretTunnels extends AbstractWorldMap{
    private final HashMap<Vector2d, Grass> grasses = new HashMap<>();
    private final HashMap<Vector2d,Tunnel> tunnels = new HashMap<>();

    public SecretTunnels(int height, int width, int initialGrassQuantity) {
        super(width,height);
        placeGrass(initialGrassQuantity);
    }

    void placeGrass(int grassQuantity) {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grassQuantity);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    void placeTunnels(){
        Random random = new Random();
        int tunnelsQuantity = random.nextInt(width*height/10+2);
        if (tunnelsQuantity%2==1){
            tunnelsQuantity +=1;
        }
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width,height,tunnelsQuantity);
        int i = 0;
        for(Vector2d tunnelsPosition: randomPositionGenerator){
            if ()
        }
    }



    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.isOccupied(position)) {
            return super.objectAt(position);
        } else if (grasses.containsKey(position)){
            return grasses.get(position);
        } else return tunnels.getOrDefault(position, null);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grasses.containsKey(position) || tunnels.containsKey(position);
    }


    @Override
    public Set<WorldElement> getElements() {
        Set<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        elements.addAll(tunnels.values());
        return elements;
    }


    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(lowerLeft, upperRight);
    }

    int getGrassSize() {
        return grasses.size();
    }
}
