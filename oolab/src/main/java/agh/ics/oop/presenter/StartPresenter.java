package agh.ics.oop.presenter;

import agh.ics.oop.model.Earth;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPresenter {

    @FXML
    public Label infoLabel;
    @FXML
    private Button startButton;

    @FXML
    public void onStartClicked() {
        try {
            Earth earth = new Earth(10,10,10);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulation.fxml"));
            Parent root = loader.load();


            SimulationPresenter simulationPresenter = loader.getController();
            simulationPresenter.setWorldMap(earth);
            earth.addMapChangeListener(simulationPresenter);

            simulationPresenter.onSimulationStartClicked();

            Stage simulationStage = new Stage();
            simulationStage.setScene(new Scene(root));
            simulationStage.show();
//            Platform.runLater(() -> startButton.setDisable(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}