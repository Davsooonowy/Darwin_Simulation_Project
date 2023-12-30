package agh.ics.oop.presenter;

import agh.ics.oop.model.Earth;
import agh.ics.oop.model.SecretTunnels;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    public TextField BehaviourVariant;

    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField initialgrassNumberField;
    @FXML
    private TextField MapVariant;
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
    public void onStartClicked() {
        try {
            int mapWidth = Integer.parseInt(widthField.getText());
            int mapHeight = Integer.parseInt(heightField.getText());
            int initialGrassNumber = Integer.parseInt(initialgrassNumberField.getText());
            int mapVariant = Integer.parseInt(MapVariant.getText());
            int animalNumber = Integer.parseInt(initialanimalsNumberField.getText());
            int initialEnergy = Integer.parseInt(startEnergyField.getText());
            int plantEnergy = Integer.parseInt(plantEnergyField.getText());
            int genomelength = Integer.parseInt(genomeLength.getText());
            int plantspawnRate = Integer.parseInt(plantSpawnRate.getText());
            int behaviourvariant = Integer.parseInt(BehaviourVariant.getText());
            int parentenergy = Integer.parseInt(parentEnergy.getText());
            int reproduceenergy = Integer.parseInt(reproduceEnergy.getText());
            int mingeneMutation = Integer.parseInt(minGeneMutation.getText());
            int maxgeneMutation = Integer.parseInt(maxGeneMutation.getText());
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
            if (mapVariant == 0) {
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