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
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.BiPredicate;
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
    @FXML
    private CheckBox generateCsvCheckBox;


    private void validateTextField(TextField textField, BiPredicate<String, Integer> validationFunction, int minVal) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!validationFunction.test(newValue, minVal)) {
            textField.setText(oldValue);
        }
    });
}

private boolean isNonNegativeInteger(String value, int minVal) {
    if (value == null || value.isEmpty()) {
        return false;
    }
    if (!value.matches("\\d*")) {
        return false;
    }
    return Integer.parseInt(value) >= minVal;
}

    private void validateTextFieldWithComparison(TextField lowerValue, TextField biggerValue) {
    lowerValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
        String text = lowerValue.getText();
        if (!newValue && !text.isEmpty()) {
            if (!text.matches("\\d*") || Integer.parseInt(text) > Integer.parseInt(biggerValue.getText())) {
                lowerValue.setText("");
            }
        } else {
            infoLabel.setText("");
        }
    });

    biggerValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
        String text = biggerValue.getText();
        if (!newValue && !text.isEmpty()) {
            if (!text.matches("\\d*") || Integer.parseInt(text) < Integer.parseInt(lowerValue.getText())) {
                biggerValue.setText("");
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
        BehaviourVariant.setItems(FXCollections.observableArrayList("Complete predestination", "An Unexpected Journey"));
        validateTextField(startEnergyField, this::isNonNegativeInteger, 0);
        validateTextField(plantEnergyField, this::isNonNegativeInteger,0);
        validateTextField(widthField, this::isNonNegativeInteger,1);
        validateTextField(heightField, this::isNonNegativeInteger,1);
        validateTextField(initialgrassNumberField, this::isNonNegativeInteger,0);
        validateTextField(initialanimalsNumberField, this::isNonNegativeInteger,0);
        validateTextField(genomeLength, this::isNonNegativeInteger,1);
        validateTextField(plantSpawnRate, this::isNonNegativeInteger,0);
        validateTextField(parentEnergy, this::isNonNegativeInteger, 0);
        validateTextField(reproduceEnergy, this::isNonNegativeInteger, 0);
        validateTextField(minGeneMutation, this::isNonNegativeInteger, 0);
        validateTextField(maxGeneMutation, this::isNonNegativeInteger, 0);
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
        boolean generateCsv = generateCsvCheckBox.isSelected();
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
        simulationPresenter.setGenerateCsv(generateCsv);
        simulationPresenter.setInitialanimalsNumberField(animalNumber);
        simulationPresenter.setInitialEnergy(initialEnergy);
        simulationPresenter.setGenomeLength(genomelength);
        simulationPresenter.setMingeneMutation(mingeneMutation);
        simulationPresenter.setMaxgeneMutation(maxgeneMutation);
        simulationPresenter.setreproduceEnergy(reproduceenergy);
        simulationPresenter.setParentEnergy(parentenergy);
        simulationPresenter.setBehaviourVariant(behaviourvariant);
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
        simulationStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}