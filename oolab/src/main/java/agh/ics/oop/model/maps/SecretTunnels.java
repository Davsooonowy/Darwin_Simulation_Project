package agh.ics.oop.model.maps;

import agh.ics.oop.model.*;
import agh.ics.oop.model.generators.TunnelGenerator;
import agh.ics.oop.model.mapObjects.Animal;
import agh.ics.oop.model.mapObjects.Tunnel;

import java.util.*;

public class SecretTunnels extends AbstractWorldMap {

    private final HashMap<Vector2d, Tunnel> tunnels = new HashMap<>();
    private final List<List<Vector2d>> tunnelsConnections;


    public SecretTunnels(int height, int width,int plantEnergy, int initialGrassQuantity, int plantSpawnRate) {
        super(width,height,plantEnergy,initialGrassQuantity,plantSpawnRate);
        Random random = new Random();
        int tunnelsQuantity = random.nextInt(width*height/10+2);
        if (tunnelsQuantity%2==1){
            tunnelsQuantity +=1;
        }
        TunnelGenerator generator = new TunnelGenerator(width, height, tunnelsQuantity);
        generator.iterator(); // This will fill the positionTuples list
        tunnelsConnections = generator.getPositionTuples();
        placeTunnels();
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

    public Tunnel getTunnel(Vector2d position){
        return tunnels.get(position);
    }

    public void wentThroughTunnel(Animal animal, Vector2d newPosition){
        animals.remove(animal.position());
        animal.goThroughTunnel(newPosition,this);
        animals.put(animal.position(), animal);
        mapChanged();
    }
}
