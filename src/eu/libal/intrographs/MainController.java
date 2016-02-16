package eu.libal.intrographs;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.GraphRenderer;
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
    private Button refreshCanvasBt;

    @FXML
    private Label cmdLabel;

    @FXML
    private TextField cmdInput;

    @FXML
    private Button execBt;

    private GraphRenderer<Integer, Edge<Integer>> graphRenderer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext context2D = mainCanvas.getGraphicsContext2D();
        renderBackground(context2D);

        Graph<Integer, Edge<Integer>> g = new Graph<>();
        g.addVertex(0, "a");
        g.addVertex(1, "b");
        g.addVertex(2, "c");
        g.addEdge("a", "b");

        graphRenderer = new GraphRenderer<>(g, mainCanvas);
        graphRenderer.render();
    }

    private void renderBackground(GraphicsContext context2D) {
        context2D.setFill(Color.WHITE);
        context2D.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    public void clearCanvas() {
        GraphicsContext context2D = mainCanvas.getGraphicsContext2D();
        context2D.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        renderBackground(context2D);
    }

    @FXML
    public void refreshCanvas(ActionEvent actionEvent) {
        clearCanvas();
        graphRenderer.render();
    }
}
