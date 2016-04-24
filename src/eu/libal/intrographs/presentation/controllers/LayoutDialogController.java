package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.presentation.layout.LayoutMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LayoutDialogController implements Initializable {

    @FXML
    public Slider runTime;

    @FXML
    public Text runTimeLabel;

    @FXML
    public Slider constantC;

    @FXML
    public Text constantCLabel;

    @FXML
    public Slider area;

    @FXML
    public Text areaLabel;

    @FXML
    public GridPane mainGrid;

    private MessageBus messageBus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runTime.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (runTimeLabel != null) {
                runTimeLabel.setText(String.format("%.0f seconds", newValue));
            }
        });

        if (constantC != null) {
            constantC.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (constantCLabel != null) {
                    constantCLabel.setText(String.format("%.0f", newValue));
                }
            });
        }

        if (area != null) {
            area.setValue(10000d);
            area.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (areaLabel != null) {
                    areaLabel.setText(String.format("%.0f", newValue));
                }
            });
        }

    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    public void handleRandomLayoutRun(ActionEvent actionEvent) throws IOException {
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("runLength", runTime.getValue());
        LayoutMessage msg = new LayoutMessage("random", vars);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream st = new ObjectOutputStream(baos);
        st.writeObject(msg);
        st.close();

        messageBus.emit("layout.run", Arrays.toString(baos.toByteArray()));
    }

    public void handleForceDirectedLayoutRun(ActionEvent actionEvent) throws IOException {
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("runLength", runTime.getValue());
        vars.put("constantC", constantC.getValue());
        vars.put("area", area.getValue());
        LayoutMessage msg = new LayoutMessage("forceDirected", vars);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream st = new ObjectOutputStream(baos);
        st.writeObject(msg);
        st.close();

        messageBus.emit("layout.run", Arrays.toString(baos.toByteArray()));
    }
}

