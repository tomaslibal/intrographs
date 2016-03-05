package eu.libal.intrographs;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
    }
}
