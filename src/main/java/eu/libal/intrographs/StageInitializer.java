package eu.libal.intrographs;

import javafx.stage.Stage;

public interface StageInitializer {
    void setStage(Stage s);

    void initFXML();
    void initScene();
    void initController();
    void initWindow();

    void initAll();
}
