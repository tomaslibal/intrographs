package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGraphDialogController implements Initializable {

    private Stage stage;
    private GraphRenderingController graphRenderingController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void newEmptyGraph(ActionEvent actionEvent) {
        if (graphRenderingController != null) {
            graphRenderingController.setGraph(new Graph<>());
        }
        if (stage != null) {
            stage.close();
        }
    }

    public void cancelNewGraph(ActionEvent actionEvent) {
        if (stage != null) {
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGraphRenderingController(GraphRenderingController graphRenderingController) {
        this.graphRenderingController = graphRenderingController;
    }
}
