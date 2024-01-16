package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.model.*;
import agh.ics.oop.model.AbstractWorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
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
    private static final Color GRASS_COLOR = javafx.scene.paint.Color.GREEN;
    private static final Color EMPTY_CELL_COLOR = javafx.scene.paint.Color.rgb(69, 38, 38);
    private static final Color TUNNEL_COLOR = javafx.scene.paint.Color.BLACK;

    private Animal trackedAnimal;

    @FXML
    private TextField mostCommonGenotypesField;
    @FXML
    private TextField totalAnimalsField;

    @FXML
    private TextField totalPlantsField;

    @FXML
    private TextField freeFieldsField;

    @FXML
    private TextField averageEnergyField;

    @FXML
    private TextField averageLifeSpanField;

    @FXML
    private TextField averageChildrenCountField;

    private AnimalStatistics animalStatistics;

    @FXML
    private TextField genomeField;

    @FXML
    private TextField activePartField;

    @FXML
    private TextField energyField;

    @FXML
    private TextField eatenPlantsField;

    @FXML
    private TextField childrenCountField;

    @FXML
    private TextField offspringCountField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField deathDayField;



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
        int gridSize = calculateGridSize(boundary);
        drawGrid(boundary, gridSize);
    }

    private int calculateGridSize(Boundary boundary) {
        int mapWidth = boundary.upperRight().getX() - boundary.lowerLeft().getX() + 1;
        int mapHeight = boundary.upperRight().getY() - boundary.lowerLeft().getY() + 1;

        int maxGridSize = Math.max(mapWidth, mapHeight);
        int cellSize = 800 / maxGridSize;

        return cellSize;
    }

    private void drawGrid(Boundary boundary, int cellSize) {
        for (int i = boundary.lowerLeft().getY(); i <= boundary.upperRight().getY(); i++) {
            for (int j = boundary.lowerLeft().getX(); j <= boundary.upperRight().getX(); j++) {
                Vector2d position = new Vector2d(j, i);
                drawGridCell(position, j - boundary.lowerLeft().getX() + 1, boundary.upperRight().getY() - i + 1, cellSize);
            }
        }
    }

    private void drawGridCell(Vector2d position, int column, int row, int cellSize) {
        WorldElement element = worldMap.objectAt(position);
        Node node = createNodeForElement(element, position, cellSize);
        mapGrid.add(node, column, row);
    }

    private boolean isAnimalStatisticsDisplayed = false;

    private void handleAnimalClick(Animal animal) {
    if (!isAnimalStatisticsDisplayed) {
        animalStatistics = new AnimalStatistics(animal, simulation);

        genomeField.setText(animalStatistics.getGenome());
        activePartField.setText(String.valueOf(animalStatistics.getActivePart()));
        energyField.setText(String.valueOf(animalStatistics.getEnergy()));
        eatenPlantsField.setText(String.valueOf(animalStatistics.getEatenPlants()));
        childrenCountField.setText(String.valueOf(animalStatistics.getChildrenCount()));
        offspringCountField.setText(String.valueOf(animalStatistics.getOffspringCount()));
        ageField.setText(String.valueOf(animalStatistics.getAge()));
        deathDayField.setText(animalStatistics.getDeathDay());
    } else {
        updateStatistics(worldMap);
    }

    isAnimalStatisticsDisplayed = !isAnimalStatisticsDisplayed;
}

    private Node createNodeForElement(WorldElement element, Vector2d position, int cellSize) {
        StackPane stackPane = createStackPane(cellSize);
        Rectangle cell = createCell(position, cellSize);
        stackPane.getChildren().add(cell);

        if (element instanceof Animal) {
            Circle circle = createAnimalCircle((Animal) element, cellSize);
            circle.setOnMouseClicked(event -> handleAnimalClick((Animal) element));
            stackPane.getChildren().add(circle);
        }

        if (worldMap instanceof SecretTunnels && ((SecretTunnels) worldMap).getTunnel(position) != null) {
            Rectangle tunnel = createTunnelRectangle(cellSize);
            stackPane.getChildren().add(tunnel);
        }

        return stackPane;
    }

    private StackPane createStackPane(int cellSize) {
        StackPane stackPane = new StackPane();
        stackPane.setMinSize(cellSize, cellSize);
        stackPane.setMaxSize(cellSize, cellSize);
        return stackPane;
    }

private Rectangle createCell(Vector2d position, int cellSize) {
    Rectangle cell = new Rectangle(cellSize, cellSize);
    if (worldMap.grassAt(position) != null) {
        if (position.equals(worldMap.getMostPreferredPosition())) {
            cell.setFill(Color.YELLOW); // Wyróżniamy preferowane przez rośliny pola na żółto
        } else {
            cell.setFill(GRASS_COLOR);
        }
    } else {
        cell.setFill(EMPTY_CELL_COLOR);
    }
    return cell;
}

    private Circle createAnimalCircle(Animal animal, int cellSize) {
    Circle circle = new Circle(cellSize / 5);
    SimulationStatistics stats = new SimulationStatistics(simulation, worldMap);
    if (animal.getGenomes().equals(stats.getDominantGenotype())) {
        circle.setFill(Color.RED); // Wyróżniamy zwierzęta z dominującym genotypem na czerwono
    } else {
        circle.setFill(animal.toColor(initialEnergy));
    }
    return circle;
}

    private Rectangle createTunnelRectangle(int cellSize) {
        Rectangle tunnel = new Rectangle(cellSize / 5, cellSize / 5);
        tunnel.setFill(TUNNEL_COLOR);
        return tunnel;
    }



    @Override
    public void mapChanged(WorldMap worldMap) {
        Platform.runLater(() -> {
            drawMap();
            updateStatistics(worldMap);
            updateAnimalStatistics();
            day++;
        });
    }

    private void updateAnimalStatistics() {
    if (animalStatistics != null) {
        genomeField.setText(animalStatistics.getGenome());
        activePartField.setText(String.valueOf(animalStatistics.getActivePart()));
        energyField.setText(String.valueOf(animalStatistics.getEnergy()));
        eatenPlantsField.setText(String.valueOf(animalStatistics.getEatenPlants()));
        childrenCountField.setText(String.valueOf(animalStatistics.getChildrenCount()));
        offspringCountField.setText(String.valueOf(animalStatistics.getOffspringCount()));
        ageField.setText(String.valueOf(animalStatistics.getAge()));
        deathDayField.setText(String.valueOf(animalStatistics.getDeathDay()));
    }
}

    private void updateStatistics(WorldMap map) {
    SimulationStatistics stats = new SimulationStatistics(simulation, (AbstractWorldMap) map);

    totalAnimalsField.setText(String.valueOf(stats.getTotalAnimals()));
    totalPlantsField.setText(String.valueOf(stats.getTotalPlants()));
    freeFieldsField.setText(String.valueOf(stats.getFreeFields()));
    averageEnergyField.setText(String.format("%.2f", stats.getAverageEnergy()));
    averageLifeSpanField.setText(String.format("%.2f", stats.getAverageLifeSpan()));
    averageChildrenCountField.setText(String.format("%.2f", stats.getAverageChildrenCount()));

    mostCommonGenotypesField.setText(stats.getMostCommonGenotypes().toString());
}


//        if(generateCsv) {
//            csvWriter.printf("%d,%d,%d,%d,%s,%.2f,%.2f,%.2f%n",
//                    day,
//                    stats.getTotalAnimals(),
//                    stats.getTotalPlants(),
//                    stats.getFreeFields(),
//                    stats.getMostCommonGenotypes().toString(),
//                    stats.getAverageEnergy(),
//                    stats.getAverageLifeSpan(),
//                    stats.getAverageChildrenCount()
//            );
//            csvWriter.flush();
//        }

    private Simulation simulation;

    @FXML
    private Button startStopButton;

    @FXML
    public void initialize() {
        startStopButton.setText("Start");
//        if(generateCsv) {
//            try {
//                csvWriter = new PrintWriter(new FileWriter("simulation_data.csv", true));
//                csvWriter.println("Day,Total Animals,Total Plants,Free Fields,Most Common Genotypes,Average Energy,Average Life Span,Average Children Count");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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