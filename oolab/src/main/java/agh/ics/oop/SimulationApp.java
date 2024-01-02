package agh.ics.oop;

import agh.ics.oop.presenter.SimulationPresenter;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends AbstractApp {

    private SimulationPresenter simulationPresenter;

    @Override
    protected String getFXMLPath() {
        return "simulation.fxml";
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        super.start(primaryStage);

        // Dodanie słuchacza do zdarzenia zamknięcia okna
        primaryStage.setOnCloseRequest(event -> {
            if (simulationPresenter.getSimulation() != null && simulationPresenter.getSimulation().isAlive()) {
                simulationPresenter.getSimulation().interrupt();
            }
        });
    }

    public void setSimulationPresenter(SimulationPresenter simulationPresenter) {
        this.simulationPresenter = simulationPresenter;
    }
}