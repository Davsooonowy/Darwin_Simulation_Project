package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.SimulationStatistics;
import agh.ics.oop.model.*;
import agh.ics.oop.model.AbstractWorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SimulationPresenter implements MapChangeListener {
    private AbstractWorldMap worldMap;
    private int initialAnimalsNumber;
    private int initialEnergy;
    private int genomeLength;
    private int mingeneMutation;
    private int maxgeneMutation;
    private int reproduceEnergy;
    private int parentEnergy;
    private String behaviourvariant;
    private SimulationEngine simulationEngine;

    @FXML
    private TextField totalAnimalsField;

    @FXML
    private TextField totalPlantsField;

    @FXML
    private TextField freeFieldsField;

    @FXML
    private TextField mostCommonGenotypesField;

    @FXML
    private TextField averageEnergyField;

    @FXML
    private TextField averageLifeSpanField;

    @FXML
    private TextField averageChildrenCountField;

    @FXML
    private LineChart<Number, Number> statisticsChart;

    private PrintWriter csvWriter;
    private boolean generateCsv;
    private int day = 0;

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

    public void setBehaviourVariant(String behaviourvariant){
        this.behaviourvariant = behaviourvariant;
    }
    public void setGenerateCsv(boolean generateCsv) {
        this.generateCsv = generateCsv;
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
        Platform.runLater(() -> {
            drawMap();
            updateStatistics(worldMap);
            day++;
        });
    }

    private void updateStatistics(WorldMap map) {
        SimulationStatistics stats = new SimulationStatistics(simulation, (AbstractWorldMap) map);
        XYChart.Series<Number, Number> series = statisticsChart.getData().get(0);
        XYChart.Series<Number, Number> totalPlantsSeries = statisticsChart.getData().get(1);
        totalPlantsSeries.getData().add(new XYChart.Data<>(day, stats.getTotalPlants()));
        XYChart.Series<Number, Number> freeFieldsSeries = statisticsChart.getData().get(2);
        freeFieldsSeries.getData().add(new XYChart.Data<>(day, stats.getFreeFields()));
        XYChart.Series<Number, Number> averageEnergySeries = statisticsChart.getData().get(3);
        averageEnergySeries.getData().add(new XYChart.Data<>(day, stats.getAverageEnergy()));
        XYChart.Series<Number, Number> averageLifeSpanSeries = statisticsChart.getData().get(4);
        averageLifeSpanSeries.getData().add(new XYChart.Data<>(day, stats.getAverageLifeSpan()));
        XYChart.Series<Number, Number> averageChildrenCountSeries = statisticsChart.getData().get(5);
        averageChildrenCountSeries.getData().add(new XYChart.Data<>(day, stats.getAverageChildrenCount()));
        series.getData().add(new XYChart.Data<>(day, stats.getTotalAnimals()));
        mostCommonGenotypesField.setText(stats.getMostCommonGenotypes().toString());
        if(generateCsv) {
            csvWriter.printf("%d,%d,%d,%d,%s,%.2f,%.2f,%.2f%n",
                    day,
                    stats.getTotalAnimals(),
                    stats.getTotalPlants(),
                    stats.getFreeFields(),
                    stats.getMostCommonGenotypes().toString(),
                    stats.getAverageEnergy(),
                    stats.getAverageLifeSpan(),
                    stats.getAverageChildrenCount()
            );
            csvWriter.flush();
        }
    }

    private Simulation simulation;

    @FXML
    private Button startStopButton;

    @FXML
    public void initialize() {
        startStopButton.setText("Start");

        XYChart.Series<Number, Number> totalAnimalsSeries = new XYChart.Series<>();
        totalAnimalsSeries.setName("Total Animals");
        statisticsChart.getData().add(totalAnimalsSeries);

        XYChart.Series<Number, Number> totalPlantsSeries = new XYChart.Series<>();
        totalPlantsSeries.setName("Total Plants");
        statisticsChart.getData().add(totalPlantsSeries);

        XYChart.Series<Number, Number> freeFieldsSeries = new XYChart.Series<>();
        freeFieldsSeries.setName("Free Fields");
        statisticsChart.getData().add(freeFieldsSeries);

        XYChart.Series<Number, Number> averageEnergySeries = new XYChart.Series<>();
        averageEnergySeries.setName("Average Energy");
        statisticsChart.getData().add(averageEnergySeries);

        XYChart.Series<Number, Number> averageLifeSpanSeries = new XYChart.Series<>();
        averageLifeSpanSeries.setName("Average Life Span");
        statisticsChart.getData().add(averageLifeSpanSeries);

        XYChart.Series<Number, Number> averageChildrenCountSeries = new XYChart.Series<>();
        averageChildrenCountSeries.setName("Average Children Count");
        statisticsChart.getData().add(averageChildrenCountSeries);
        statisticsChart.setLegendVisible(true);
        if(generateCsv) {
            try {
                csvWriter = new PrintWriter(new FileWriter("simulation_data.csv", true));
                csvWriter.println("Day,Total Animals,Total Plants,Free Fields,Most Common Genotypes,Average Energy,Average Life Span,Average Children Count");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
public void onStartStopButtonClicked() {
    try {
        if (simulationEngine == null) {
            simulationEngine = new SimulationEngine(new ArrayList<>());
            simulation = new Simulation(initialAnimalsNumber, worldMap, initialEnergy, genomeLength,reproduceEnergy, parentEnergy, mingeneMutation,maxgeneMutation, behaviourvariant);
            simulationEngine.getSimulations().add(simulation);
            simulationEngine.runAsync();
            startStopButton.setText("Stop");
        } else if (simulationEngine.isRunning()) {
            simulationEngine.pauseSimulation();
            startStopButton.setText("Start");
        } else {
            simulationEngine.resumeSimulation();
            startStopButton.setText("Stop");
        }} catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

    @FXML
    private GridPane mapGrid;
}