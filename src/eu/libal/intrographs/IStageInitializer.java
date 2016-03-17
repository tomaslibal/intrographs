package eu.libal.intrographs;

import javafx.stage.Stage;

public interface IStageInitializer {
    void setStage(Stage s);

    void initFXML();
    void initScene();
    void initController();
    void initWindow();

    void initAll();
}
