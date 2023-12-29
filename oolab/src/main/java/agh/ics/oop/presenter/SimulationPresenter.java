package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private AbstractWorldMap worldMap;
    private int initialAnimalsNumber;
    private int initialEnergy;
    private int genomeLength;

    public void setGenomeLength(int genomeLength){
        this.genomeLength=genomeLength;
    }

    public void setInitialEnergy(int initialEnergy){
        this.initialEnergy = initialEnergy;
    }


    public void setWorldMap(AbstractWorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void setInitialanimalsNumberField(int initialanimalsNumberField) {
        this.initialAnimalsNumber = initialanimalsNumberField;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawAxes(Boundary boundary) {
        drawXAxis(boundary);
        drawYAxis(boundary);
        Label label = new Label("x/y");
        label.setMinWidth(50);
        label.setMinHeight(50);
        label.setAlignment(Pos.CENTER);
        mapGrid.add(label, 0, 0);


    }

    private void drawXAxis(Boundary boundary) {
        for (int j = boundary.lowerLeft().getX(); j <= boundary.upperRight().getX(); j++) {
            Label label = new Label(Integer.toString(j));
            label.setMinWidth(50);
            label.setMinHeight(50);
            label.setAlignment(Pos.CENTER);
            mapGrid.add(label, j + 1 - boundary.lowerLeft().getX(), 0);
        }
    }

    private void drawYAxis(Boundary boundary) {
        for (int i = boundary.lowerLeft().getY(); i <= boundary.upperRight().getY(); i++) {
            Label label = new Label(Integer.toString(i));
            label.setMinWidth(50);
            label.setMinHeight(50);
            label.setAlignment(Pos.CENTER);
            mapGrid.add(label, 0, boundary.upperRight().getY() - i + 1);
        }
    }

    @FXML
    public void drawMap() {
        clearGrid();
        Boundary boundary = worldMap.getCurrentBounds();
        drawAxes(boundary);
        drawGrid(boundary);
    }

    private void drawGrid(Boundary boundary) {
        for (int i = boundary.lowerLeft().getY(); i <= boundary.upperRight().getY(); i++) {
            for (int j = boundary.lowerLeft().getX(); j <= boundary.upperRight().getX(); j++) {
                Vector2d position = new Vector2d(j, i);
                drawGridCell(position, j - boundary.lowerLeft().getX() + 1, boundary.upperRight().getY() - i + 1);
            }
        }
    }

    private void drawGridCell(Vector2d position, int column, int row) {
        WorldElement element = worldMap.objectAt(position);
        Label label = createLabelForElement(element);
        mapGrid.add(label, column, row);
    }

    private Label createLabelForElement(WorldElement element) {
        Label label;
        if (element != null) {
            label = new Label(element.toString());
        } else {
            label = new Label(" ");
        }
        label.setMinWidth(50);
        label.setMinHeight(50);
        label.setAlignment(Pos.CENTER);
        return label;
    }


    @Override
    public void mapChanged(WorldMap worldMap) {
        Platform.runLater(this::drawMap);
    }

    private Simulation simulation;

    @FXML
    private Button startStopButton;

    @FXML
    public void initialize() {
        startStopButton.setText("Start");
    }

    @FXML
    public void onStartStopButtonClicked() {
        try {
            if (simulation == null) {
                simulation = new Simulation(initialAnimalsNumber, worldMap, initialEnergy, genomeLength);
                simulation.start();
                startStopButton.setText("Stop");
            } else if (simulation.isRunning()) {
                simulation.pauseSimulation();
                startStopButton.setText("Start");
            } else {
                simulation.resumeSimulation();
                startStopButton.setText("Stop");
            }} catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private GridPane mapGrid;
}