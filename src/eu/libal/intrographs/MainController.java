package eu.libal.intrographs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class MainController implements Initializable {

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Button clearCanvasBt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void clearCanvas(ActionEvent actionEvent) {
        GraphicsContext context2D = mainCanvas.getGraphicsContext2D();
        context2D.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }
}
