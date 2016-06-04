package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.JavaFXThreadingRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MainControllerTest {

    @Rule
    public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();

    private MainController controller;

    @Before
    public void setUp() throws Exception {
        controller = new MainController();
    }


    @Test
    public void shouldStartWithChangedSetToFalse() {
        assertThat(controller.getFileChanged().getValue(), is(false));
    }

}