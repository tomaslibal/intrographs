package eu.libal.intrographs;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.presentation.controllers.VertexDetailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoStageInitializer implements IStageInitializer {

    private FXMLLoader loader;
    private Stage stage;
    private MessageBus messageBus;
    private Parent root;
    private Scene scene;
    private Stage primary;
    private VertexDetailController controller;

    public InfoStageInitializer(Stage primary) {
        this(primary, null);
    }

    public InfoStageInitializer(Stage primary, Stage stage) {
        this(primary, stage, new MessageBus());
    }

    public InfoStageInitializer(Stage primary, Stage stage, MessageBus messageBus) {
        this.primary = primary;
        this.stage = stage;
        this.messageBus = messageBus;
    }

    @Override
    public void setStage(Stage s) {
        stage = s;
    }

    @Override
    public void initFXML() {
        loader = new FXMLLoader(getClass().getResource("presentation/views/vertexDetail.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initScene() {
        scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void initController() {
        controller = loader.getController();
        controller.setMessageBus(messageBus);
    }

    @Override
    public void initWindow() {
        stage.setTitle("Vertex Details");
        stage.initOwner(primary);
    }

    @Override
    public void initAll() {
        initFXML();
        initScene();
        initController();
        initWindow();
    }
}
