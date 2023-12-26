package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;

public class SecretTunnels extends AbstractWorldMap{
    private final HashMap<Vector2d, Grass> grasses = new HashMap<>();
    private final HashMap<Vector2d,Tunnel> tunnels = new HashMap<>();
    private List<List<Vector2d>> tunnelsConnections = new ArrayList<>();

    private List<WorldElement> elements = new ArrayList<>();

    public SecretTunnels(int height, int width, int initialGrassQuantity) {
        super(width,height);
        placeGrass(initialGrassQuantity);
        Random random = new Random();
        int tunnelsQuantity = random.nextInt(width*height/10+2);
        if (tunnelsQuantity%2==1){
            tunnelsQuantity +=1;
        }
        TunnelGenerator generator = new TunnelGenerator(width, height, tunnelsQuantity);
        generator.iterator(); // This will fill the positionTuples list
        tunnelsConnections = generator.getPositionTuples();
        placeTunnels();
        System.out.println(tunnels);
        System.out.println(tunnelsConnections);
    }

    void placeGrass(int grassQuantity) {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grassQuantity);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    void placeTunnels(){
        for(List<Vector2d> tunnelConnection : tunnelsConnections){
            tunnels.put(tunnelConnection.get(0),new Tunnel(tunnelConnection.get(0),tunnelConnection.get(1)));
            tunnels.put(tunnelConnection.get(1),new Tunnel(tunnelConnection.get(1),tunnelConnection.get(0)));
        }
    }



    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.isOccupied(position)) {
            return super.objectAt(position);
        } else if (tunnels.containsKey(position)){
            return tunnels.get(position);
        } else return grasses.getOrDefault(position, null);
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

    @Override
    public List<WorldElement> objectsAt(Vector2d position) {
        return elements.stream()
                .filter(element -> element.getPosition().equals(position))
                .collect(Collectors.toList());
    }

    public Tunnel getTunnel(Vector2d position){
        return tunnels.get(position);
    }

    public void wentThroughTunnel(Animal animal, Vector2d newPosition){
        animals.remove(animal.getPosition());
        animal.goThroughTunnel(newPosition,this);
        animals.put(animal.getPosition(), animal);
        mapChanged();
    }
}
