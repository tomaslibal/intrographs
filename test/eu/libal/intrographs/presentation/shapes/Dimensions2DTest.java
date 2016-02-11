package eu.libal.intrographs.presentation.shapes;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
public class Dimensions2DTest {

    Dimensions2D dims;

    @Before
    public void setUp() throws Exception {
        dims = new Dimensions2D();
    }

    @Test
    public void shouldInit_With_Zero_Zero_Dimensions() {
        assertThat(dims.getWidth(), is(0));
        assertThat(dims.getHeight(), is(0));
    }

    @Test
    public void shouldInit_With_GivenDimensions() {
        Dimensions2D d = new Dimensions2D(42, 43);

        assertThat(d.getWidth(), is(42));
        assertThat(d.getHeight(), is(43));
    }
}