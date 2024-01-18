package agh.ics.oop.model.maps;
import agh.ics.oop.model.mapObjects.Animal;
import agh.ics.oop.model.mapObjects.Tunnel;
import agh.ics.oop.model.vector_records.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SecretTunnelsTest {
    @Test
    void tunnelstest(){
        SecretTunnels secretTunnels = new SecretTunnels(2, 2, 10, 10, 10);
        List<List<Vector2d>> tunnelsConnections = new ArrayList<>();
        HashMap<Vector2d, Tunnel> tunnels = new HashMap<>();
        List<Vector2d> tunnel1 = new ArrayList<>();
        tunnel1.add(new Vector2d(0,0));
        tunnel1.add(new Vector2d(1,1));
        tunnelsConnections.add(tunnel1);
        tunnels.put(tunnelsConnections.get(0).get(0),new Tunnel(tunnelsConnections.get(0).get(0),tunnelsConnections.get(0).get(1)));
        tunnels.put(tunnelsConnections.get(0).get(1),new Tunnel(tunnelsConnections.get(0).get(1),tunnelsConnections.get(0).get(0)));
        secretTunnels.setTunnelsConnections(tunnelsConnections);
        secretTunnels.setTunnels(tunnels);
        Animal animal1 = new Animal(new Vector2d(0,0), 10, 2, 10, 10, 1, 1);
        animal1.goThroughTunnel(animal1.getPosition(),secretTunnels);
        assertEquals(new Vector2d(1,1), animal1.getPosition());
    }
}
