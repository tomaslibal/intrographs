package eu.libal.intrographs.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class AboutDialogController implements Initializable {

    @FXML
    public Button okBt;

    @FXML
    public GridPane mainGrid;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
    }

    public void handleOKBtAction(ActionEvent actionEvent) {
        if (stage != null) {
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleHomepageLinkClick(ActionEvent actionEvent) throws IOException {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/tomaslibal/intrographs"));
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
}
