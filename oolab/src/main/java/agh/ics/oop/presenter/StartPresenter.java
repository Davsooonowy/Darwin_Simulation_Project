package agh.ics.oop.presenter;

import agh.ics.oop.model.maps.Earth;
import agh.ics.oop.model.maps.SecretTunnels;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

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
    private ChoiceBox<String> BehaviourVariant;

    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField initialgrassNumberField;
    @FXML
    private ChoiceBox<String> MapVariant;
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
    @FXML
    private Button loadConfigButton;

    @FXML
    private Button saveConfigButton;
    @FXML
    private TextField loadConfigIdField;

    @FXML
    private TextField saveConfigNameField;



    ///                                validate text fields                                                ///
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
        if (!newValue && !text.isEmpty() && text.matches("\\d*")) {
            int lowerNumber = Integer.parseInt(text);
            String biggerText = biggerValue.getText();
            if (biggerText.matches("\\d*")) {
                int biggerNumber = Integer.parseInt(biggerText);
                if (lowerNumber > biggerNumber) {
                    lowerValue.setText("");
                }
            }
        } else {
            infoLabel.setText("");
        }
    });

    biggerValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
        String text = biggerValue.getText();
        if (!newValue && !text.isEmpty() && text.matches("\\d*")) {
            int biggerNumber = Integer.parseInt(text);
            String lowerText = lowerValue.getText();
            if (lowerText.matches("\\d*")) {
                int lowerNumber = Integer.parseInt(lowerText);
                if (biggerNumber < lowerNumber) {
                    biggerValue.setText("");
                }
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
        try {


            ///                               set values and validate in choice boxes            ///
            MapVariant.setItems(FXCollections.observableArrayList("Earth", "Secret Tunnels"));
            BehaviourVariant.setItems(FXCollections.observableArrayList("Complete predestination", "An Unexpected Journey"));
            validateTextField(startEnergyField, this::isNonNegativeInteger, 0);
            validateTextField(plantEnergyField, this::isNonNegativeInteger, 0);
            validateTextField(widthField, this::isNonNegativeInteger, 1);
            validateTextField(heightField, this::isNonNegativeInteger, 1);
            validateTextField(initialgrassNumberField, this::isNonNegativeInteger, 0);
            validateTextField(initialanimalsNumberField, this::isNonNegativeInteger, 0);
            validateTextField(genomeLength, this::isNonNegativeInteger, 1);
            validateTextField(plantSpawnRate, this::isNonNegativeInteger, 0);
            validateTextField(parentEnergy, this::isNonNegativeInteger, 0);
            validateTextField(reproduceEnergy, this::isNonNegativeInteger, 0);
            validateTextField(minGeneMutation, this::isNonNegativeInteger, 0);
            validateTextField(maxGeneMutation, this::isNonNegativeInteger, 0);
            validateTextFieldWithComparison(minGeneMutation, maxGeneMutation);
            validateTextFieldWithComparison(parentEnergy, reproduceEnergy);
            validateTextFieldWithComparison(maxGeneMutation, genomeLength);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadConfigButton.setOnAction(event -> {
            String configId = loadConfigIdField.getText();
            if (!configId.isEmpty()) {
                try {
                    loadConfigurations(configId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        saveConfigButton.setOnAction(event -> {
            String configName = saveConfigNameField.getText();
            if (!configName.isEmpty()) {
                Map<String, String> configs = new HashMap<>();
                configs.put("width", widthField.getText());
                configs.put("height", heightField.getText());
                configs.put("startEnergy", startEnergyField.getText());
                configs.put("plantEnergy", plantEnergyField.getText());
                configs.put("initialAnimalsNumber", initialanimalsNumberField.getText());
                configs.put("initialGrassNumber", initialgrassNumberField.getText());
                configs.put("plantSpawnRate", plantSpawnRate.getText());
                configs.put("reproduceEnergy", reproduceEnergy.getText());
                configs.put("parentEnergy", parentEnergy.getText());
                configs.put("minGeneMutation", minGeneMutation.getText());
                configs.put("maxGeneMutation", maxGeneMutation.getText());
                configs.put("genomeLength", genomeLength.getText());
                configs.put("mapVariant", (String) MapVariant.getValue());
                configs.put("behaviourVariant", (String) BehaviourVariant.getValue());
                configs.put("generateCsv", String.valueOf(generateCsvCheckBox.isSelected()));

                try {
                    saveConfigurations(configName, configs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveConfigurations(String configName, Map<String, String> configs) throws IOException {
    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
    Map<String, Map<String, String>> allConfigs;

    // Read existing configurations
    try (FileReader reader = new FileReader("configurations.json")) {
        allConfigs = gson.fromJson(reader, type);
        if (allConfigs == null) {
            allConfigs = new HashMap<>();
        }
    }
        configs.put("mapVariant", MapVariant.getValue().equals("Earth") ? "1" : "0");
        configs.put("behaviourVariant", BehaviourVariant.getValue().equals("Complete predestination") ? "1" : "0");
        configs.put("generateCsv", generateCsvCheckBox.isSelected() ? "1" : "0");
    // Add new configuration
        allConfigs.put(configName, configs);

    // Write all configurations back to file
    String json = gson.toJson(allConfigs);
    try (FileWriter writer = new FileWriter("configurations.json")) {
        writer.write(json);
    }
}

    public void loadConfigurations(String configName) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();

        try (FileReader reader = new FileReader("configurations.json")) {
            if (reader.ready()) {
                Map<String, Map<String, String>> allConfigs = gson.fromJson(reader, type);

                if (allConfigs != null) {
                    Map<String, String> configs = allConfigs.get(configName);

                    if (configs != null) {
                        widthField.setText(configs.get("width"));
                        heightField.setText(configs.get("height"));
                        startEnergyField.setText(configs.get("startEnergy"));
                        plantEnergyField.setText(configs.get("plantEnergy"));
                        initialanimalsNumberField.setText(configs.get("initialAnimalsNumber"));
                        initialgrassNumberField.setText(configs.get("initialGrassNumber"));
                        plantSpawnRate.setText(configs.get("plantSpawnRate"));
                        reproduceEnergy.setText(configs.get("reproduceEnergy"));
                        parentEnergy.setText(configs.get("parentEnergy"));
                        minGeneMutation.setText(configs.get("minGeneMutation"));
                        maxGeneMutation.setText(configs.get("maxGeneMutation"));
                        genomeLength.setText(configs.get("genomeLength"));
                        String mapVariantValue = configs.get("mapVariant").equals("1") ? "Earth" : "Secret Tunnels";
                        String behaviourVariantValue = configs.get("behaviourVariant").equals("1") ? "Complete predestination" : "An Unexpected Journey";

                        MapVariant.getSelectionModel().select(mapVariantValue);
                        BehaviourVariant.getSelectionModel().select(behaviourVariantValue);
                        generateCsvCheckBox.setSelected(configs.get("generateCsv").equals("1"));

                    } else {
                        System.out.println("Configuration with name " + configName + " not found.");
                    }
                } else {
                    System.out.println("No configurations found.");
                }
            } else {
                System.out.println("Configuration file is empty.");
            }
        }
    }

    @FXML
public void onStartClicked() {
    try {
        ///                                    get values from text fields                           ///
        boolean generateCsv = generateCsvCheckBox.isSelected();
        String selectedOption = MapVariant.getValue();
        String behaviourvariant = BehaviourVariant.getValue();
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

        ///                                    set values in simulation presenter                ///
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
            Earth worldMap = new Earth(mapHeight, mapWidth, plantEnergy, initialGrassNumber, plantspawnRate);
            simulationPresenter.setWorldMap(worldMap);
            worldMap.addMapChangeListener(simulationPresenter);
        } else {
            SecretTunnels worldMap = new SecretTunnels(mapHeight, mapWidth, plantEnergy, initialGrassNumber, plantspawnRate);
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