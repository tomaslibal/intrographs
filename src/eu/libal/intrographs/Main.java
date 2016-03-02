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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("presentation/views/main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Intrographs 2");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
