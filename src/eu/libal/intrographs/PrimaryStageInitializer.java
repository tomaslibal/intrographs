package eu.libal.intrographs;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.presentation.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimaryStageInitializer implements StageInitializer {

    private Stage stage;
    private Stage infoStage;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private MainController controller;
    private MessageBus messageBus;

    public PrimaryStageInitializer(Stage primary) {
        this(primary, null);
    }

    public PrimaryStageInitializer(Stage primary, Stage info) {
        this(primary, info, new MessageBus());
    }

    public PrimaryStageInitializer(Stage primary, Stage info, MessageBus messageBus) {
        this.stage = primary;
        this.infoStage = info;
        this.messageBus = messageBus;
    }

    @Override
    public void setStage(Stage s) {
        stage = s;
    }

    @Override
    public void initFXML() {
        loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));

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
        controller.setStage(stage);
        controller.setInfoWindowStage(infoStage);
        controller.setMessageBus(messageBus);
    }

    @Override
    public void initWindow() {
        stage.setTitle("Intrographs 2");
    }

    @Override
    public void initAll() {
        initFXML();
        initScene();
        initController();
        initWindow();
    }
}
