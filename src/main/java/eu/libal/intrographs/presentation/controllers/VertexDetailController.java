package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.common.MessageBus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class VertexDetailController implements Initializable {

    @FXML
    public GridPane mainGrid;

    @FXML
    public GridPane auxGrid;

    @FXML
    public TextField vValInput;

    @FXML
    public TextField vIDInput;

    @FXML
    public ColorPicker colorPicker;

    private MessageBus messageBus;

    private String selectedVertexId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auxGrid.setPadding(new Insets(10, 10, 10, 10));
        mainGrid.setPadding(new Insets(10, 10, 10, 10));

        noVertexSelected();
    }

    @FXML
    public void handleUpdateBtAction(ActionEvent actionEvent) {
        messageBus.emit("vertex.update", "oldId:" + selectedVertexId + ";newId:" + vIDInput.getText() + ";newVal:" + vValInput.getText());
    }


    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;

        messageBus.subscribe("vertex.selected", vId -> {
            selectedVertexId = vId;
            vertexSelected();
        });

        messageBus.subscribe("graph.vertex.remove", msg -> {
            noVertexSelected();
        });
    }

    @FXML
    public void handleRemoveBtAction(ActionEvent actionEvent) {
        if (selectedVertexId != null) {
            messageBus.emit("vertex.remove", selectedVertexId);
            noVertexSelected();
            selectedVertexId = null;
        }
    }

    private void noVertexSelected() {
        mainGrid.setVisible(false);
        auxGrid.setVisible(true);
    }

    private void vertexSelected() {
        mainGrid.setVisible(true);
        auxGrid.setVisible(false);
    }

    public void handleColorPickerAction(ActionEvent actionEvent) {
        messageBus.emit("vertex.color.update", selectedVertexId + ";" + colorPicker.getValue().toString());
    }
}
