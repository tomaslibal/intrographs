package eu.libal.intrographs;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("INTROGRAPHS2");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage infoWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("presentation/views/main.fxml"));
        Parent root = loader.load();
        Scene primaryStageScene = new Scene(root);
        primaryStage.setScene(primaryStageScene);
        MainController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setInfoWindowState(infoWindow);

        primaryStage.setTitle("Intrographs 2");

        primaryStage.show();

        Parent infoWindowRoot = FXMLLoader.load(getClass().getResource("presentation/views/vertexDetail.fxml"));

        infoWindow.addEventHandler(EventType.ROOT, event -> {
        });

        Scene secondScene = new Scene(infoWindowRoot);
        infoWindow.setY(100);
        infoWindow.setX(100);
        infoWindow.setTitle("Vertex Details");
        infoWindow.setScene(secondScene);
        infoWindow.show();
    }
}
