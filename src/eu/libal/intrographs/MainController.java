package eu.libal.intrographs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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

    @FXML
    private Label cmdLabel;

    @FXML
    private TextField cmdInput;

    @FXML
    private Button execBt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext context2D = mainCanvas.getGraphicsContext2D();
        renderBackground(context2D);
    }

    private void renderBackground(GraphicsContext context2D) {
        context2D.setFill(Color.WHITE);
        context2D.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    @FXML
    public void clearCanvas(ActionEvent actionEvent) {
        GraphicsContext context2D = mainCanvas.getGraphicsContext2D();
        context2D.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        renderBackground(context2D);
    }
}
