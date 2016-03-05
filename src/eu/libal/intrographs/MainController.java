package eu.libal.intrographs;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.DepthFirstSearch;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.CanvasStates;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Instant;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 */
public class MainController implements Initializable {

    @FXML
    public Button addVertexBt;

    @FXML
    public Button addEdgeBt;

    @FXML
    public GridPane mainGrid;

    @FXML
    public CheckBox labelsCheckbox;

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

    private CanvasStates canvasState = CanvasStates.PANNING;

    private Graph<Integer, Edge<Integer>> g;


    /**
     * Points to the stage of the main window
     */
    private Stage stage;

    /**
     * A reference to the info window stage so that this window/controller can access nodes in that window.
     */
    private Stage infoWindowStage;
    private GraphRenderingController graphRenderingController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MessageBus messageBus = new MessageBus();

        graphRenderingController = new GraphRenderingController();
        graphRenderingController.setCanvas(mainCanvas);
        graphRenderingController.setMessageBus(messageBus);
        graphRenderingController.setup();
        graphRenderingController.update();

//        g = new Graph<>();
//        g.addVertex(0, "a");
//        g.addVertex(1, "b");
//        g.addVertex(2, "c");
//        g.addEdge("a", "b");
//
//        graphRenderer = new GraphRenderer<>(g, mainCanvas);
//        graphRenderer.render();

        mainCanvas.widthProperty().addListener(observable -> {
//            graphRenderer.render();
            graphRenderingController.update();
        });
        mainCanvas.heightProperty().addListener(observable -> {
//            graphRenderer.render();
            graphRenderingController.update();
        });

        mainGrid.widthProperty().addListener((observable, oldGridWidth, newGridWidth) -> {
            /*
             * This condition is a guard against the first width change which goes from 0 to whatever the initial
             * width is. Without this the canvas would get its width added together with the grid's width on init.
             */
            if (oldGridWidth.intValue() > 640) {
                double originalCanvasWidth = mainCanvas.getWidth();
                mainCanvas.setWidth(originalCanvasWidth + (newGridWidth.doubleValue() - oldGridWidth.doubleValue()));
            }
        });

        messageBus.subscribe("#addVertexBt.text.change", newLabel -> addVertexBt.setText(newLabel));
        messageBus.subscribe("#addEdgeBt.text.change", newLabel -> addEdgeBt.setText(newLabel));
        messageBus.subscribe("#vIDInput.text.change", newText -> {
            TextField idNode = (TextField) infoWindowStage.getScene().lookup("#vIDInput");
            idNode.setText(newText);
        });
        messageBus.subscribe("#vValInput.text.change", newText -> {
            TextField node = (TextField) infoWindowStage.getScene().lookup("#vValInput");
            node.setText(newText);
        });
        messageBus.subscribe("Cursor.cursor.change", newCursorId -> stage.getScene().setCursor(Cursor.cursor(newCursorId)));
    }

    @FXML
    public void refreshCanvas(ActionEvent actionEvent) {
        graphRenderingController.update();
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        graphRenderingController.handleMouseClick(event);
    }

    @FXML
    public void setCanvasStatusToAddingVertex(ActionEvent actionEvent) {
        addVertexBt.setText("Click on canvas");
        graphRenderingController.setCanvasState(CanvasStates.ADDING_VERTEX);
    }

    @FXML
    public void setCanvasStatusToAddingEdge(ActionEvent actionEvent) {
        addEdgeBt.setText("Choose vertices");
        graphRenderingController.setCanvasState(CanvasStates.ADDING_EDGE);
    }

    @FXML
    public void handleLabelsToggle(MouseEvent event) {
        if (labelsCheckbox.isSelected()) {
            graphRenderingController.displayLabels();
        } else {
            graphRenderingController.hideLabels();
        }
    }

    public void handleDrag(MouseEvent event) {
        graphRenderingController.handleMouseDrag(event);
    }

    @FXML
    public void handleMousePress(MouseEvent event) {
        graphRenderingController.handleMousePress(event);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleMouseRelease(MouseEvent event) {
        graphRenderingController.handleMouseRelease(event);
    }

    @FXML
    public void handleMenuExitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void handleMouseMoved(MouseEvent event) {
        graphRenderingController.handleMouseMoved(event);
    }

    public void setInfoWindowState(Stage infoWindow) {
        infoWindowStage = infoWindow;
    }
}
