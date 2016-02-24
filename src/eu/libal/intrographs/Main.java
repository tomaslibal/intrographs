package eu.libal.intrographs;

import javafx.application.Application;
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
        Parent root = FXMLLoader.load(getClass().getResource("presentation/views/main.fxml"));

        primaryStage.setTitle("Intrographs 2");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
