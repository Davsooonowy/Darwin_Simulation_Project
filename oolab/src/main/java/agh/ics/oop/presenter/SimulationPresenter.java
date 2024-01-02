package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.AbstractWorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SimulationPresenter implements MapChangeListener {
    private AbstractWorldMap worldMap;
    private int initialAnimalsNumber;
    private int initialEnergy;
    private int genomeLength;
    private int mingeneMutation;
    private int maxgeneMutation;
    private int reproduceEnergy;
    private int parentEnergy;
    private Animal trackedAnimal;



    public void setMingeneMutation(int mingeneMutation){
        this.mingeneMutation=mingeneMutation;
    }

    public void setMaxgeneMutation(int maxgeneMutation){
        this.maxgeneMutation=maxgeneMutation;
    }

    public void setGenomeLength(int genomeLength){
        this.genomeLength=genomeLength;
    }

    public void setreproduceEnergy(int reproduceEnergy){
        this.reproduceEnergy=reproduceEnergy;
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

    public void setParentEnergy(int parentEnergy){
        this.parentEnergy=parentEnergy;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @FXML
    public void drawMap() {
        clearGrid();
        Boundary boundary = worldMap.getCurrentBounds();
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
        Node node = createNodeForElement(element, position);
        mapGrid.add(node, column, row);
    }

    private Node createNodeForElement(WorldElement element, Vector2d position) {
    StackPane stackPane = createStackPane();
    Rectangle cell = createCell(position);
    stackPane.getChildren().add(cell);

    if (element instanceof Animal) {
        Circle circle = createAnimalCircle((Animal) element);
        stackPane.getChildren().add(circle);
    }

    if (worldMap instanceof SecretTunnels && ((SecretTunnels) worldMap).getTunnel(position) != null) {
        Rectangle tunnel = createTunnelRectangle();
        stackPane.getChildren().add(tunnel);
    }

    return stackPane;
}

    private StackPane createStackPane() {
        StackPane stackPane = new StackPane();
        stackPane.setMinSize(50, 50);
        stackPane.setMaxSize(50, 50);
        return stackPane;
    }

    private Rectangle createCell(Vector2d position) {
        Rectangle cell = new Rectangle(50, 50);
        if (worldMap.grassAt(position) != null) {
            cell.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            cell.setFill(javafx.scene.paint.Color.rgb(69, 38, 38));
        }
        return cell;
    }

    private Circle createAnimalCircle(Animal animal) {
        Circle circle = new Circle(10);
        circle.setFill(animal.toColor(initialEnergy));
        return circle;
    }

    private Rectangle createTunnelRectangle() {
        Rectangle tunnel = new Rectangle(10, 10);
        tunnel.setFill(javafx.scene.paint.Color.BLACK);
        return tunnel;
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
                simulation = new Simulation(initialAnimalsNumber, worldMap, initialEnergy, genomeLength,reproduceEnergy, parentEnergy, mingeneMutation,maxgeneMutation);
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
    public Simulation getSimulation() {
        return this.simulation;
    }

    @FXML
    private GridPane mapGrid;
}