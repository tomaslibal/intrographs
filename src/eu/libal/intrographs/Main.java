package eu.libal.intrographs;

import eu.libal.intrographs.presentation.StageLayoutFormatter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("INTROGRAPHS2");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageLayoutFormatter stageFormatter = new StageLayoutFormatter();
        Stage infoStage = new Stage();
        PrimaryStageInitializer primaryStageInitializer = new PrimaryStageInitializer(primaryStage, infoStage);
        InfoStageInitializer infoStageInitializer = new InfoStageInitializer(primaryStage, infoStage);

        primaryStageInitializer.initAll();
        infoStageInitializer.initAll();

        stageFormatter.setPrimary(primaryStage);
        stageFormatter.setInfo(infoStage);
        stageFormatter.format();

        primaryStage.show();
        infoStage.show();
    }
}
