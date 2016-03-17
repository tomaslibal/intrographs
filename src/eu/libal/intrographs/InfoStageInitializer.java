package eu.libal.intrographs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class InfoStageInitializer implements IStageInitializer {

    private FXMLLoader loader;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private Stage primary;

    public InfoStageInitializer(Stage primary) {
        this.primary = primary;
    }

    public InfoStageInitializer(Stage primary, Stage stage) {
        this.primary = primary;
        this.stage = stage;
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