package eu.libal.intrographs.presentation;

import javafx.stage.Stage;

/**
 * Formats the stages of the application view such that they are arranged into a desirable grid. Main stage,
 * i.e. the main window which renders the graph is the primary stage.
 *
 */
public class StageLayoutFormatter {

    /**
     * The primary stage of the application. This is created in the Main class by the JavaFX framework and passed into
     * Main#start(Stage primaryStage).
     */
    private Stage primary;

    /**
     * The secondary stage currently displays information about the selected vertex. This is used as an auxiliary stage.
     */
    private Stage info;

    public StageLayoutFormatter() {

    }

    public void setPrimary(Stage primary) {
        this.primary = primary;
    }

    public void setInfo(Stage info) {
        this.info = info;
    }

    public void format() {
        formatPrimaryStage();
        formatInfoStage();
    }

    public void formatInfoStage() {
        if (info != null) {
            double infoOffsetX = 100;
            info.setX(infoOffsetX);
            info.setY(100);
        }
    }

    public void formatPrimaryStage() {
        if (primary != null) {
            double primaryOffsetX = 380;
            primary.setX(primaryOffsetX);
            double primaryOffsetY = 100;
            primary.setY(primaryOffsetY);
        }
    }
}
