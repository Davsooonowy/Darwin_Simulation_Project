package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ParametersDialogController {

    @FXML
    private TextField widthTextField;

    @FXML
    private TextField heightTextField;
    // Add more fields for other parameters

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void onStartClicked() {
        // Get values from text fields and pass them to SimulationApp
        int width = Integer.parseInt(widthTextField.getText());
        int height = Integer.parseInt(heightTextField.getText());
        // Extract values for other parameters

        // Close the dialog
        dialogStage.close();

        // Now, you can pass these parameters to SimulationApp and create the simulation
        // Example: SimulationApp.createSimulation(width, height, ...);
    }

    public TextField getWidthTextField() {
        return widthTextField;
    }

    public TextField getHeightTextField() {
        return heightTextField;
    }
    // Add more getters for other parameters
}
