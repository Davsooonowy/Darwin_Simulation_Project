package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.Statistics.AnimalStatistics;
import agh.ics.oop.Statistics.SimulationStatistics;
import agh.ics.oop.model.mapObjects.Animal;
import agh.ics.oop.model.mapObjects.WorldElement;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.maps.MapChangeListener;
import agh.ics.oop.model.maps.SecretTunnels;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.vector_records.Boundary;
import agh.ics.oop.model.vector_records.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulationPresenter implements MapChangeListener {

    // fields
    private AbstractWorldMap worldMap;
    private int initialAnimalsNumber;
    private int initialEnergy;
    private int genomeLength;
    private int mingeneMutation;
    private int maxgeneMutation;
    private int reproduceEnergy;
    private int parentEnergy;
    private String behaviourvariant;
    private boolean isAnimalStatisticsDisplayed = false;
    private static final Color GRASS_COLOR = javafx.scene.paint.Color.GREEN;
    private static final Color EMPTY_CELL_COLOR = javafx.scene.paint.Color.rgb(69, 38, 38);
    private static final Color TUNNEL_COLOR = javafx.scene.paint.Color.BLACK;
    private Simulation simulation;
    private AnimalStatistics animalStatistics;
    private PrintWriter csvWriter;
    private boolean generateCsv;
    private int day = 0;


    ///                                             FXML fields                                              ///
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

    @FXML
    private Label currentDayField;

    @FXML
    private Button startStopButton;
    @FXML
    private GridPane mapGrid;


    ///                                             setters                                                    ///


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

    ///                                map drawing and calculations                               ///

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @FXML
    public void drawMap() {
        clearGrid();
        Boundary boundary = worldMap.getBounds();
        int gridSize = calculateGridSize(boundary);
        drawGrid(boundary, gridSize);
    }

    private int calculateGridSize(Boundary boundary) {
        int mapWidth = boundary.upperRight().x() - boundary.lowerLeft().x() + 1;
        int mapHeight = boundary.upperRight().y() - boundary.lowerLeft().y() + 1;

        int maxGridSize = Math.max(mapWidth, mapHeight);
        int cellSize = 800 / maxGridSize;

        return cellSize;
    }

    private void drawGrid(Boundary boundary, int cellSize) {
        for (int i = boundary.lowerLeft().y(); i <= boundary.upperRight().y(); i++) {
            for (int j = boundary.lowerLeft().x(); j <= boundary.upperRight().x(); j++) {
                Vector2d position = new Vector2d(j, i);
                drawGridCell(position, j - boundary.lowerLeft().x() + 1, boundary.upperRight().y() - i + 1, cellSize);
            }
        }
    }

    private void drawGridCell(Vector2d position, int column, int row, int cellSize) {
        WorldElement element = worldMap.objectAt(position);
        Node node = createNodeForElement(element, position, cellSize);
        mapGrid.add(node, column, row);
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
            cell.setFill(GRASS_COLOR);
        } else {
            cell.setFill(EMPTY_CELL_COLOR);
        }
        return cell;
    }

    @Override
    public void mapChanged(WorldMap worldMap) {
        Platform.runLater(() -> {
            drawMap();
            updateStatistics(worldMap);
            updateAnimalStatistics();
            updateDay();
            day++;
        });
    }

    ///                                animal methods                                                    ///
    private Circle createAnimalCircle(Animal animal, int cellSize) {
        Circle circle = new Circle(cellSize / 5);
        circle.setFill(animal.toColor(initialEnergy));
        return circle;
    }

    ///                                tunnel methods                                                    ///
    private Rectangle createTunnelRectangle(int cellSize) {
        Rectangle tunnel = new Rectangle(cellSize / 5, cellSize / 5);
        tunnel.setFill(TUNNEL_COLOR);
        return tunnel;
    }

    ///                               statistics methods                                                 ///
    private void updateStatistics(WorldMap map) {
    SimulationStatistics stats = new SimulationStatistics(simulation, (AbstractWorldMap) map);

    totalAnimalsField.setText(String.valueOf(stats.getTotalAnimals()));
    totalPlantsField.setText(String.valueOf(stats.getTotalPlants()));
    freeFieldsField.setText(String.valueOf(stats.getFreeFields()));
    averageEnergyField.setText(String.format("%.2f", stats.getAverageEnergy()));
    averageLifeSpanField.setText(String.format("%.2f", stats.getAverageLifeSpan()));
    averageChildrenCountField.setText(String.format("%.2f", stats.getAverageChildrenCount()));

    mostCommonGenotypesField.setText(stats.getMostCommonGenotypes().toString());

    if(generateCsv) {
        csvWriter.printf("%d,%d,%d,%d,\"%s\",%.2f,%.2f,%.2f%n",
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

    ///                                day methods                                                       ///
    public void updateDay() {
        currentDayField.setText(String.valueOf(simulation.getCurrentDay()));
    }

    ///                                start/stop methods                                               ///
    @FXML
    public void initialize() {
        startStopButton.setText("Start");
    }

@FXML
public void onStartStopButtonClicked() {
    try {
        if (simulation == null) {
            if (generateCsv) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                LocalDateTime now = LocalDateTime.now();
                String filename = "simulation_data_" + dtf.format(now) + ".csv";
                csvWriter = new PrintWriter(new FileWriter(filename, true));
                csvWriter.println("Day,Total Animals,Total Plants,Free Fields,Most Common Genotypes,Average Energy,Average Life Span,Average Children Count");
            }
            simulation = new Simulation(initialAnimalsNumber, worldMap, initialEnergy, genomeLength,reproduceEnergy, parentEnergy, mingeneMutation,maxgeneMutation, behaviourvariant);
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
}