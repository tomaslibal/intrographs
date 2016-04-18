package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.common.ListenableField;
import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.CanvasStates;
import eu.libal.intrographs.presentation.StageTitle;
import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexLabelShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.*;

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

    /**
     * Reference to the stage of the main window
     */
    private Stage stage;

    /**
     * Reference to the info window stage so that this window/controller can access nodes in that window.
     */
    private Stage infoWindowStage;
    private GraphRenderingController graphRenderingController;
    private MessageBus messageBus;

    /**
     * Keeps track whether the current open file has changed or not. Saving the graph into a file reverts the
     * state to changed = false. Any other change changes the state to true.
     *
     */
    private ListenableField<Boolean> fileChanged = new ListenableField<>(false);

    private StageTitle mainStageTitle = new StageTitle("Intrographs2", "no_name.gdt");

    public ListenableField<Boolean> getFileChanged() {
        return fileChanged;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        graphRenderingController = new GraphRenderingController();
        graphRenderingController.setCanvas(mainCanvas);
        graphRenderingController.setup();
        graphRenderingController.update();

        mainCanvas.widthProperty().addListener(observable -> {
            graphRenderingController.update();
        });
        mainCanvas.heightProperty().addListener(observable -> {
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
    }

    private void setButtonText(Button button, String text) {
        button.setText(text);
    }

    private void subscribeToGraphRenderingCtrlEvents(MessageBus messageBus) {
        messageBus.subscribe("#addVertexBt.text.change", newLabel -> setButtonText(addVertexBt, newLabel));

        messageBus.subscribe("#addEdgeBt.text.change", newLabel -> setButtonText(addEdgeBt, newLabel));

        /**
         * This event is emitted when a vertex is selected
         */
        messageBus.subscribe("#vIDInput.text.change", newText -> {
            TextField idNode = (TextField) infoWindowStage.getScene().lookup("#vIDInput");
            idNode.setText(newText);
            ColorPicker colorPicker = (ColorPicker) infoWindowStage.getScene().lookup("#colorPicker");
            Optional<VertexShape2D<Integer>> vertexShape2D = graphRenderingController.getGraphRenderer().getVertexShapes().stream().filter(vs -> vs.getVertex().getId().equals(newText)).findFirst();
            Color vertexColor = vertexShape2D.flatMap(shape -> Optional.of(shape.getVertexColor())).orElseGet(VertexShape2D::getDefaultColor);
            colorPicker.setValue(vertexColor);
            messageBus.emit("vertex.selected", newText);
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

    public void setCanvasState(CanvasStates state) {
        graphRenderingController.setCanvasState(state);
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
        String buttonCss = this.getClass().getResource("/styles/button.css").toExternalForm();
        this.stage.getScene().getStylesheets().add(buttonCss);

        stage.setTitle(mainStageTitle.toString());

        fileChanged.subscribe("update", bool -> {
            if (Boolean.valueOf(bool)) {
                mainStageTitle.setSuffix("(changed)");
            } else {
                mainStageTitle.setSuffix("");
            }
            stage.setTitle(mainStageTitle.toString());
        });
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

    public void setInfoWindowStage(Stage infoWindow) {
        infoWindowStage = infoWindow;
    }

    @FXML
    public void handleAboutAction(ActionEvent actionEvent) {

        Pair<AboutDialogController, Stage> about = createNewModalDialog("About", getClass().getResource("/views/aboutDialog.fxml"));

        about.getKey().setStage(about.getValue());

        about.getValue().show();
    }

    @FXML
    public void handleViewVerticesAction(ActionEvent actionEvent) {
        Pair<VertexSetListController<Integer>, Stage> newVertexSetDialog = createNewModalDialog(
                "Vertex Set",
                getClass().getResource("/views/vertexSetList.fxml")
        );

        VertexSetListController<Integer> controller = newVertexSetDialog.getKey();
        controller.setData(graphRenderingController.getGraph().vertexSet());

        Stage dialog = newVertexSetDialog.getValue();
        dialog.show();
    }

    public void setCanvasStatusToRemovingVertex(ActionEvent actionEvent) {
        graphRenderingController.setCanvasState(CanvasStates.REMOVING_VERTEX);
    }

    public void setCanvasStatusToRemovingEdge(ActionEvent actionEvent) {
        graphRenderingController.setCanvasState(CanvasStates.REMOVING_EDGE);
    }

    @FXML
    public void handleNewGraphAction(ActionEvent actionEvent) {
        Pair<NewGraphDialogController, Stage> newModalDialog = createNewModalDialog(
                "Choose new graph",
                getClass().getResource("/views/newGraphDialog.fxml")
        );

        Stage stage = newModalDialog.getValue();
        NewGraphDialogController controller = newModalDialog.getKey();

        controller.setGraphRenderingController(graphRenderingController);
        controller.setStage(stage);
        controller.setPrimaryStage(this.stage);
        controller.setMessageBus(this.messageBus);

        stage.show();
    }

    private <T extends Initializable> Pair<T, Stage> createNewModalDialog(String title, URL fxmlResource) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle(title);

        FXMLLoader loader = new FXMLLoader(fxmlResource);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        T controller = loader.getController();

        dialog.setScene(new Scene(root));
        dialog.initOwner(stage);

        return new Pair<>(controller, dialog);
    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
        graphRenderingController.setMessageBus(this.messageBus);
        subscribeToGraphRenderingCtrlEvents(this.messageBus);
        subscribeToVertexUpdateEvents(this.messageBus);
        subscribeToFileChangedEvents(this.messageBus);
    }

    private void subscribeToFileChangedEvents(MessageBus messageBus) {
        messageBus.subscribe("graphFile.changed", strBool -> {
            fileChanged.setValue(Boolean.valueOf(strBool));
        });
    }

    private void subscribeToVertexUpdateEvents(MessageBus messageBus) {
        messageBus.subscribe("vertex.update", msg -> {
            String[] parts = msg.split(";");
            if (parts.length == 3) {
                String oldId = parts[0].substring(6);
                String newId = parts[1].substring(6);
                String newVal = parts[2].substring(7);

                Optional<Vertex<Integer>> lookupVertex = graphRenderingController.getGraph().lookupVertex(oldId);

                lookupVertex.flatMap(vertex -> {
                    vertex.setId(newId);
                    vertex.setValue(Integer.valueOf(newVal));

                    Set<Edge> edges = graphRenderingController.getGraph().edgeSet();

                    edges.forEach(edge -> {
                        // check that the edge target/source is not the oldId, if so, update the id to newId
                        if (edge.getSource().getId().equals(oldId)) {
                            edge.setSource(vertex);
                        } else if(edge.getTarget().getId().equals(oldId)) {
                            edge.setTarget(vertex);
                        }
                    });

                    return Optional.of(vertex);
                });
            }
        });
    }

    public void handleViewEdgesAction(ActionEvent actionEvent) {
        Pair<EdgeSetListController, Stage> newEdgeSetDialog = createNewModalDialog(
                "Edge Set",
                getClass().getResource("/views/edgeSetList.fxml")
        );

        EdgeSetListController controller = newEdgeSetDialog.getKey();
        controller.setData(graphRenderingController.getGraph().edgeSet());

        Stage dialog = newEdgeSetDialog.getValue();
        dialog.show();
    }

    @FXML
    public void handleForceDirectedLayoutAction(ActionEvent actionEvent) {
        graphRenderingController.updateLayoutForceDirected();
    }

    public void handleRandomLayoutAction(ActionEvent actionEvent) {
        graphRenderingController.updateLayoutRandom();
    }

    public void handleSaveAsAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Graph File");
        fileChooser.setInitialFileName("graph.gdt");
        File graphFile = fileChooser.showSaveDialog(stage);

        if (graphFile != null) {
            FileOutputStream ostream = new FileOutputStream(graphFile.getPath());
            ObjectOutputStream p = new ObjectOutputStream(ostream);
            p.writeObject(graphRenderingController.getGraph());
            p.writeObject(graphRenderingController.getGraphRenderer().getVerticesWithLabels());
            p.writeObject(graphRenderingController.getGraphRenderer().getEdgeShapes());
            p.flush();
            ostream.close();
        }
    }

    public void handleOpenGraphAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Graph File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Graph files", "*.gdt"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File graphFile = fileChooser.showOpenDialog(stage);

        if (graphFile != null) {
            FileInputStream istream = new FileInputStream(graphFile.getPath());
            ObjectInputStream p = new ObjectInputStream(istream);
            Graph<Integer, Edge> graph = (Graph<Integer, Edge>) p.readObject();
            HashMap<VertexShape2D<Integer>, VertexLabelShape2D> vertexShapes = (HashMap<VertexShape2D<Integer>, VertexLabelShape2D>) p.readObject();
            HashSet<EdgeShape2D> edgeShapes = (HashSet<EdgeShape2D>) p.readObject();
            istream.close();

            graphRenderingController.getGraphRenderer().setVerticesWithLabels(vertexShapes);
            graphRenderingController.getGraphRenderer().setEdgeShapes(edgeShapes);
            graphRenderingController.setGraph(graph);
        }
    }
}
