package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.graphs.edge.Edge;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class EdgeSetListController implements Initializable {

    @FXML
    public GridPane mainGrid;

    @FXML
    public TableView<Edge> edgeSet;

    private ObservableList<Edge> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGrid.setPadding(new Insets(10, 10, 10, 10));

        TableColumn<Edge, String> sourceCol = new TableColumn<>("Source");
        TableColumn<Edge, String> targetCol = new TableColumn<>("Target");
        //TableColumn<Double, Double> weightCol = new TableColumn<>("Weight");

        sourceCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSource().getId()));
        targetCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTarget().getId()));

        edgeSet.getColumns().addAll(sourceCol, targetCol);

        edgeSet.setItems(data);
    }

    public void setData(Set<Edge> data) {
        this.data = FXCollections.observableArrayList();
        this.data.addAll(data);
        edgeSet.setItems(this.data);
    }
}
