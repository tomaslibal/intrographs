package eu.libal.intrographs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimaryStageInitializer implements IStageInitializer {

    private Stage stage;
    private Stage infoStage;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private MainController controller;

    public PrimaryStageInitializer(Stage infoStage) {
        this.infoStage = infoStage;
    }

    public PrimaryStageInitializer(Stage primary, Stage info) {
        this.stage = primary;
        this.infoStage = info;
    }

    @Override
    public void setStage(Stage s) {
        stage = s;
    }

    @Override
    public void initFXML() {
        loader = new FXMLLoader(getClass().getResource("presentation/views/main.fxml"));

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
