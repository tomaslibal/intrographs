package eu.libal.intrographs;

import eu.libal.intrographs.presentation.controllers.NewGraphDialogController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class NewGraphDialogControllerTest {

    private NewGraphDialogController controller;

    @Rule
    public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();

    @Before
    public void setup() {
        controller = new NewGraphDialogController();
    }

    @Test
    public void shouldVoid() {

    }
}