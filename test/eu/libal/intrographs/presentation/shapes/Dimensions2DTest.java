package eu.libal.intrographs.presentation.shapes;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
public class Dimensions2DTest {

    private Dimensions2D dims;

    @Before
    public void setUp() throws Exception {
        dims = new Dimensions2D();
    }

    @Test
    public void shouldInitWithZeroZeroDimensions() {
        assertThat(dims.getWidth(), is(0));
        assertThat(dims.getHeight(), is(0));
    }

    @Test
    public void shouldInitWithGivenDimensions() {
        Dimensions2D d = new Dimensions2D(42, 43);

        assertThat(d.getWidth(), is(42));
        assertThat(d.getHeight(), is(43));
    }

    @Test
    public void shouldUpdateDimensionsWithGivenValues() {
        Dimensions2D d = new Dimensions2D(42, 43);

        d.set(101, 102);

        assertThat(d.getWidth(), is(101));
        assertThat(d.getHeight(), is(102));
    }
}