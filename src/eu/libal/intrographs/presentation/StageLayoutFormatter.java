package eu.libal.intrographs.presentation;

import javafx.stage.Stage;

public class StageLayoutFormatter {

    private Stage primary;
    private Stage info;
    private double primaryOffsetX = 380;
    private double primaryOffsetY = 100;
    private double infoOffsetX = 100;

    public StageLayoutFormatter() {

    }

    public Stage getPrimary() {
        return primary;
    }

    public void setPrimary(Stage primary) {
        this.primary = primary;
    }

    public Stage getInfo() {
        return info;
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
            info.setX(infoOffsetX);
            info.setY(100);
        }
    }

    public void formatPrimaryStage() {
        if (primary != null) {
            primary.setX(primaryOffsetX);
            primary.setY(primaryOffsetY);
        }
    }
}
