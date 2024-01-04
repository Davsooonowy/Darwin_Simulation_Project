package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.Earth;
import agh.ics.oop.model.SecretTunnels;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;

public class StartPresenter {

    @FXML
    public Label infoLabel;
    @FXML
    public TextField parentEnergy;
    @FXML
    public TextField reproduceEnergy;
    @FXML
    public TextField minGeneMutation;
    @FXML
    public TextField maxGeneMutation;
    @FXML
    public ChoiceBox BehaviourVariant;

    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField initialgrassNumberField;
    @FXML
    private ChoiceBox MapVariant;
    @FXML
    private TextField initialanimalsNumberField;
    @FXML
    private TextField startEnergyField;
    @FXML
    private TextField plantEnergyField;
    @FXML
    private TextField genomeLength;
    @FXML
    private TextField plantSpawnRate;
    @FXML
    private Button startButton;
    private SimulationEngine simulationEngine;
    private Simulation simulation;


    private void validateTextField(TextField textField, Predicate<String> validationFunction) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!validationFunction.test(newValue)) {
                textField.setText(oldValue);
            }
        });
    }

    private boolean isNonNegativeInteger(String value) {
        return value.matches("\\d*") && Integer.parseInt(value) >= 0;
    }

    private void validateTextFieldWithComparison(TextField lowerValue, TextField biggerValue) {
    lowerValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
        String text = lowerValue.getText();
        if (!newValue) {
            if (!text.matches("\\d*") || Integer.parseInt(text) > Integer.parseInt(biggerValue.getText())) {
                lowerValue.setText("");
                infoLabel.setText(text + " is not legal input in " + lowerValue.getId() + "TextField");
            }
        } else {
            infoLabel.setText("");
        }
    });

    biggerValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
        String text = biggerValue.getText();
        if (!newValue) {
            if (!text.matches("\\d*") || Integer.parseInt(text) < Integer.parseInt(lowerValue.getText())) {
                biggerValue.setText("");
                infoLabel.setText(text + " is not legal input in " + biggerValue.getId() + "TextField");
            }
        } else {
            infoLabel.setText("");
        }
    });
}

    private int parseTextFieldToInt(TextField textField) {
        return Integer.parseInt(textField.getText());
    }
    @FXML
    public void initialize() {
        MapVariant.setItems(FXCollections.observableArrayList("Earth", "Secret Tunnels"));
        BehaviourVariant.setItems(FXCollections.observableArrayList("a", "dup"));
        validateTextField(startEnergyField, this::isNonNegativeInteger);
        validateTextField(plantEnergyField, this::isNonNegativeInteger);
        validateTextField(widthField, this::isNonNegativeInteger);
        validateTextField(heightField, this::isNonNegativeInteger);
        validateTextField(initialgrassNumberField, this::isNonNegativeInteger);
        validateTextField(initialanimalsNumberField, this::isNonNegativeInteger);
        validateTextField(genomeLength, this::isNonNegativeInteger);
        validateTextField(plantSpawnRate, this::isNonNegativeInteger);
        validateTextField(parentEnergy, this::isNonNegativeInteger);
        validateTextField(reproduceEnergy, this::isNonNegativeInteger);
        validateTextField(minGeneMutation, this::isNonNegativeInteger);
        validateTextField(maxGeneMutation, this::isNonNegativeInteger);
        validateTextFieldWithComparison(minGeneMutation,maxGeneMutation);
        validateTextFieldWithComparison(parentEnergy,reproduceEnergy);
        validateTextFieldWithComparison(maxGeneMutation,genomeLength);
        validateTextFieldWithComparison(minGeneMutation, genomeLength);

        BooleanBinding areFieldsEmpty = Bindings.createBooleanBinding(() ->
                        startEnergyField.getText().isEmpty() ||
                        plantEnergyField.getText().isEmpty() ||
                        widthField.getText().isEmpty() ||
                        heightField.getText().isEmpty() ||
                        initialgrassNumberField.getText().isEmpty() ||
                        initialanimalsNumberField.getText().isEmpty() ||
                        genomeLength.getText().isEmpty() ||
                        plantSpawnRate.getText().isEmpty() ||
                        parentEnergy.getText().isEmpty() ||
                        reproduceEnergy.getText().isEmpty() ||
                        minGeneMutation.getText().isEmpty() ||
                        maxGeneMutation.getText().isEmpty() ||
                        MapVariant.getValue() == null ||
                        BehaviourVariant.getValue() == null,
                startEnergyField.textProperty(),
                plantEnergyField.textProperty(),
                widthField.textProperty(),
                heightField.textProperty(),
                initialgrassNumberField.textProperty(),
                initialanimalsNumberField.textProperty(),
                genomeLength.textProperty(),
                plantSpawnRate.textProperty(),
                parentEnergy.textProperty(),
                reproduceEnergy.textProperty(),
                minGeneMutation.textProperty(),
                maxGeneMutation.textProperty(),
                MapVariant.valueProperty(),
                BehaviourVariant.valueProperty()
        );

        startButton.disableProperty().bind(areFieldsEmpty);
    }


    @FXML
    public void onStartClicked() {
        try {
            String selectedOption = (String) MapVariant.getValue();
            String behaviourvariant = (String) BehaviourVariant.getValue();
            int mapWidth = parseTextFieldToInt(widthField);
            int mapHeight = parseTextFieldToInt(heightField);
            int initialGrassNumber = parseTextFieldToInt(initialgrassNumberField);
            int animalNumber = parseTextFieldToInt(initialanimalsNumberField);
            int initialEnergy = parseTextFieldToInt(startEnergyField);
            int plantEnergy = parseTextFieldToInt(plantEnergyField);
            int genomelength = parseTextFieldToInt(genomeLength);
            int plantspawnRate = parseTextFieldToInt(plantSpawnRate);
            int parentenergy = parseTextFieldToInt(parentEnergy);
            int reproduceenergy = parseTextFieldToInt(reproduceEnergy);
            int mingeneMutation = parseTextFieldToInt(minGeneMutation);
            int maxgeneMutation = parseTextFieldToInt(maxGeneMutation);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Parent root = loader.load();

            SimulationPresenter simulationPresenter = loader.getController();
            simulationPresenter.setInitialanimalsNumberField(animalNumber);
            simulationPresenter.setInitialEnergy(initialEnergy);
            simulationPresenter.setGenomeLength(genomelength);
            simulationPresenter.setMingeneMutation(mingeneMutation);
            simulationPresenter.setMaxgeneMutation(maxgeneMutation);
            simulationPresenter.setreproduceEnergy(reproduceenergy);
            simulationPresenter.setParentEnergy(parentenergy);
            if (selectedOption.equals("Earth")) {
                Earth worldMap = new Earth(mapWidth, mapHeight, plantEnergy, initialGrassNumber, plantspawnRate);
                simulationPresenter.setWorldMap(worldMap);
                worldMap.addMapChangeListener(simulationPresenter);
            } else {
                SecretTunnels worldMap = new SecretTunnels(mapWidth, mapHeight, plantEnergy, initialGrassNumber, plantspawnRate);
                simulationPresenter.setWorldMap(worldMap);
                worldMap.addMapChangeListener(simulationPresenter);
            }

            simulationPresenter.onStartStopButtonClicked();

            Stage simulationStage = new Stage();
            simulationStage.setScene(new Scene(root));
            simulationStage.setOnCloseRequest(event -> {
                simulationEngine.interruptSimulation(simulation.getSimulationId());
            });
            simulationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}