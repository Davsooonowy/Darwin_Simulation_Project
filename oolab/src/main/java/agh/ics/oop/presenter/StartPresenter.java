package agh.ics.oop.presenter;

import agh.ics.oop.model.Earth;
import agh.ics.oop.model.SecretTunnels;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private boolean isInRange0To8(String value) {
        return value.matches("[0-8]*");
    }

    private void validateTextFieldWithComparison(TextField textFieldToValidate, TextField textFieldToCompare) {
    textFieldToValidate.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*") || Integer.parseInt(newValue) > Integer.parseInt(textFieldToCompare.getText())) {
            textFieldToValidate.setText(oldValue);
        }
    });

    textFieldToCompare.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*") || Integer.parseInt(newValue) < Integer.parseInt(textFieldToValidate.getText())) {
            textFieldToCompare.setText(oldValue);
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
        validateTextField(minGeneMutation, this::isInRange0To8);
        validateTextField(maxGeneMutation, this::isInRange0To8);
        validateTextFieldWithComparison(minGeneMutation,maxGeneMutation);
        validateTextFieldWithComparison(parentEnergy,reproduceEnergy);
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
            simulationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}