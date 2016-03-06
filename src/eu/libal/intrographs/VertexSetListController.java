package eu.libal.intrographs;

import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 *
 */
public class VertexSetListController implements Initializable {

    @FXML
    public GridPane mainGrid;

    @FXML
    public TableView<Vertex<?>> vertexSet;

    private ObservableList<Vertex<?>> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGrid.setPadding(new Insets(10, 10, 10, 10));

        TableColumn idCol = new TableColumn("Id");
        TableColumn valueCol = new TableColumn("Value");

        idCol.setCellValueFactory(new PropertyValueFactory<Vertex<?>, Integer>("id"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Vertex<?>, String>("value"));

        vertexSet.getColumns().addAll(idCol, valueCol);

        vertexSet.setItems(data);
    }


    public void setData(Set<Vertex<Integer>> data) {
        this.data = FXCollections.observableArrayList();
        this.data.addAll(data);
        vertexSet.setItems(this.data);
    }
}
