package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.common.MessageBus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGraphFromAdjMatrixController implements Initializable {

    @FXML
    public TextArea textAreaAdjMatrix;
    private MessageBus messageBus;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void handleOKAction(ActionEvent actionEvent) {
        String adjMatrixString = textAreaAdjMatrix.getText();

        if (messageBus != null) {
            messageBus.emit("new.graph.fromAdjacencyMatrix", adjMatrixString);
        }

        if (stage != null) {
            stage.close();
        }
    }

    public void handleCancelAction(ActionEvent actionEvent) {
        textAreaAdjMatrix.setText("");
        stage.close();
    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
