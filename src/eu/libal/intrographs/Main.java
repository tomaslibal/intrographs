package eu.libal.intrographs;

import eu.libal.intrographs.edge.Edge;
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
//        Graph<Integer, Edge<Integer>> g = new Graph<>();
//        g.addVertex(1);
//        g.addVertex(2);

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        primaryStage.setTitle("Intrographs 2");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
