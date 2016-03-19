package eu.libal.intrographs;

import eu.libal.intrographs.common.MessageBus;
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
        MessageBus messageBus = new MessageBus();
        StageLayoutFormatter stageFormatter = new StageLayoutFormatter();
        Stage infoStage = new Stage();
        PrimaryStageInitializer primaryStageInitializer = new PrimaryStageInitializer(primaryStage, infoStage, messageBus);
        InfoStageInitializer infoStageInitializer = new InfoStageInitializer(primaryStage, infoStage, messageBus);

        primaryStageInitializer.initAll();
        infoStageInitializer.initAll();

        stageFormatter.setPrimary(primaryStage);
        stageFormatter.setInfo(infoStage);
        stageFormatter.format();

        primaryStage.show();
        infoStage.show();
    }
}
