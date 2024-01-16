package agh.ics.oop.model;

import agh.ics.oop.model.maps.WorldMap;

public interface MapChangeListener {
    void mapChanged(WorldMap worldMap);
}
