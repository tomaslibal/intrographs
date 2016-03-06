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
public class VertexSetListController<T> implements Initializable {

    @FXML
    public GridPane mainGrid;

    @FXML
    public TableView<Vertex<T>> vertexSet;

    private ObservableList<Vertex<T>> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainGrid.setPadding(new Insets(10, 10, 10, 10));

        TableColumn<Vertex<T>, String> idCol = new TableColumn<>("Id");
        TableColumn<Vertex<T>, T> valueCol = new TableColumn<>("Value");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        vertexSet.getColumns().addAll(idCol, valueCol);

        vertexSet.setItems(data);
    }


    public void setData(Set<Vertex<T>> data) {
        this.data = FXCollections.observableArrayList();
        this.data.addAll(data);
        vertexSet.setItems(this.data);
    }
}
