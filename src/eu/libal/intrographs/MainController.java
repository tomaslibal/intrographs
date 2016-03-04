package eu.libal.intrographs;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.CanvasStates;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.math.BigInteger;
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
     * x coordinate of a mouse click
     */
    private double cx;
    /**
     * y coordinate of a mouse click
     */
    private double cy;

    /**
     * x coordinate of the origin
     */
    private double ox = 0;
    /**
     * y coordinate of the origin
     */
    private double oy = 0;

    /*
     * When adding a new edge this auxiliary variable holds the reference to the source vertex of a new edge.
     */
    private VertexShape2D sel1;
    private Stage stage;
    private double dx = 0;
    private double dy = 0;

    private VertexShape2D translateVertex;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        g = new Graph<>();
        g.addVertex(0, "a");
        g.addVertex(1, "b");
        g.addVertex(2, "c");
        g.addEdge("a", "b");

        graphRenderer = new GraphRenderer<>(g, mainCanvas);
        graphRenderer.render();

        mainCanvas.widthProperty().addListener(observable -> {
            graphRenderer.render();
        });
        mainCanvas.heightProperty().addListener(observable -> {
            graphRenderer.render();
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
    }

    @FXML
    public void refreshCanvas(ActionEvent actionEvent) {
        graphRenderer.render();
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (canvasState == CanvasStates.ADDING_VERTEX) {
            String id = String.valueOf(Instant.now().getEpochSecond());
            g.addVertex(id, event.getX() - ox, event.getY() - oy);
            canvasState = CanvasStates.PANNING;
            addVertexBt.setText("Add vertex");
        }

        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (canvasState == CanvasStates.ADDING_EDGE && selectedVertex.isPresent()) {

            if (sel1 == null) {
                sel1 = selectedVertex.get();
            } else {
                VertexShape2D sel2 = selectedVertex.get();
                g.addEdge(sel1.getVertexId(), sel2.getVertexId());

                sel1 = null;
                canvasState = CanvasStates.PANNING;
                addEdgeBt.setText("Add Edge");
            }
        }
    }

    @FXML
    public void setCanvasStatusToAddingVertex(ActionEvent actionEvent) {
        addVertexBt.setText("Click on canvas");
        canvasState = CanvasStates.ADDING_VERTEX;
    }

    @FXML
    public void setCanvasStatusToAddingEdge(ActionEvent actionEvent) {
        addEdgeBt.setText("Choose vertices");
        canvasState = CanvasStates.ADDING_EDGE;
    }

    private Optional<VertexShape2D> getVertexAtMouseClick(MouseEvent click) {

        int x = (int) Math.round(click.getX() - ox);
        int y = (int) Math.round(click.getY() - oy);

        int leniency = 5;
        int radius = 5;

        return graphRenderer.getVertexShapes().stream()
                .filter(shape -> {
                    int dx = Math.abs(shape.getX() - x);
                    int dy = Math.abs(shape.getY() - y);

                    return dx < (leniency + radius) && dy < (leniency + radius);
                })
                .findFirst();
    }

    @FXML
    public void handleLabelsToggle(MouseEvent event) {
        if (labelsCheckbox.isSelected()) {
            graphRenderer.displayLabels();
        } else {
            graphRenderer.hideLabels();
        }
    }

    public void handleDrag(MouseEvent event) {

        double prevDx = dx;
        double prevDy = dy;

        dx = event.getX() - cx;
        dy = event.getY() - cy;

        double tx = dx - prevDx;
        double ty = dy - prevDy;

        if (canvasState == CanvasStates.TRANSLATING_VERTEX && translateVertex != null) {
            Integer x = translateVertex.getX();
            Integer y = translateVertex.getY();
            translateVertex.setX(x + Double.valueOf( tx ).intValue());
            translateVertex.setY(y + Double.valueOf( ty ).intValue());
            graphRenderer.render();
        } else {
            ox += tx;
            oy += ty;

            graphRenderer.render(ox, oy);
        }
    }

    @FXML
    public void handleMousePress(MouseEvent event) {
        cx = event.getX();
        cy = event.getY();

        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent() && canvasState == CanvasStates.PANNING) {
            stage.getScene().setCursor(Cursor.CLOSED_HAND);
            canvasState = CanvasStates.TRANSLATING_VERTEX;
            translateVertex = selectedVertex.get();
        }

        if (!selectedVertex.isPresent() && canvasState == CanvasStates.TRANSLATING_VERTEX) {
            canvasState = CanvasStates.PANNING;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleMouseRelease(MouseEvent event) {
        System.out.println("mouse released");
        dx = 0;
        dy = 0;

        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent()) {
            stage.getScene().setCursor(Cursor.HAND);
        } else {
            stage.getScene().setCursor(Cursor.DEFAULT);
        }

        if (canvasState == CanvasStates.TRANSLATING_VERTEX) {
            canvasState = CanvasStates.PANNING;
        }
        translateVertex = null;
    }

    @FXML
    public void handleMenuExitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void handleMouseMoved(MouseEvent event) {
        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent()) {
            stage.getScene().setCursor(Cursor.HAND);
        } else {
            stage.getScene().setCursor(Cursor.DEFAULT);
        }
    }
}
