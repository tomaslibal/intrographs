package eu.libal.intrographs;

import eu.libal.intrographs.common.MessageBus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
    public TextField vValInput;

    @FXML
    public TextField vIDInput;

    private MessageBus messageBus;

    private String selectedVertexId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
    }

    @FXML
    public void handleUpdateBtAction(ActionEvent actionEvent) {
        messageBus.emit("vertex.update", "");
    }


    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;

        messageBus.subscribe("vertex.selected", vId -> {
            selectedVertexId = vId;
        });
    }

    @FXML
    public void handleRemoveBtAction(ActionEvent actionEvent) {
        if (selectedVertexId != null) {
            messageBus.emit("vertex.remove", selectedVertexId);
            selectedVertexId = null;
        }
    }
}
