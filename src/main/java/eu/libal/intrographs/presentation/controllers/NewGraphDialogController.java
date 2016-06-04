package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.Graph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGraphDialogController implements Initializable {

    @FXML
    public GridPane mainGrid;

    private Stage stage;
    private GraphRenderingController graphRenderingController;
    private Stage primaryStage;
    private MessageBus messageBus;

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

    public void newGraphFromAdjMatrix(ActionEvent actionEvent) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("New graph from adjacency matrix");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/fromAdjacencyMatrix.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NewGraphFromAdjMatrixController controller = loader.getController();

        controller.setMessageBus(messageBus);
        controller.setStage(dialog);

        dialog.setScene(new Scene(root));
        dialog.initOwner(primaryStage);
        dialog.show();
        stage.close();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
    }
}
