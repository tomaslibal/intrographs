package eu.libal.intrographs;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.presentation.controllers.LayoutDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LayoutStageInitializer implements StageInitializer {

    private final Stage primary;
    private final MessageBus messageBus;
    private final HashMap<String, String> fxmlLayouts = new HashMap<>();
    private Stage stage;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private LayoutDialogController controller;
    private String layoutFilePath;

    LayoutStageInitializer(Stage primary, MessageBus messageBus) {
        this.primary = primary;
        this.messageBus = messageBus;
        this.fxmlLayouts.put("forceDirected", "/views/layoutForceDirectedDialog.fxml");
        this.fxmlLayouts.put("random", "/views/layoutRandomDialog.fxml");

        setStage(new Stage());

        this.messageBus.subscribe("layout.dialog.show", layoutStr -> {
            layoutFilePath = fxmlLayouts.get(layoutStr);
            initAll();

            if (stage != null) {
                stage.setX(primary.getX() - 200 - 10);
                stage.setY(primary.getY() + 200 + 300);
                stage.setTitle("Layout dialog");

                stage.show();
            }
        });

        this.messageBus.subscribe("layout.dialog.hide", strMsg -> {
            if (stage != null) {
                stage.hide();
            }
        });
    }

    @Override
    public void setStage(Stage s) {
        stage = s;
    }

    @Override
    public void initFXML() {
        loader = new FXMLLoader(getClass().getResource(layoutFilePath));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initScene() {
        scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void initController() {
        controller = loader.getController();
        controller.setMessageBus(messageBus);
    }

    @Override
    public void initWindow() {
        if (stage.getOwner() == null) {
            stage.initOwner(primary);
        }
    }

    @Override
    public void initAll() {
        initFXML();
        initScene();
        initController();
        initWindow();
    }
}
