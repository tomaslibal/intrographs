package eu.libal.intrographs.presentation.shapes;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
public class Coordinates2DTest {

    Coordinates2D coords;

    @Before
    public void setUp() throws Exception {
        coords = new Coordinates2D();
    }

    @Test
    public void shouldInit_WithVector_Zero_Zero_WhenNoArgumentsPassedToConstructor() {
        assertThat(coords.getX(), is(0));
        assertThat(coords.getY(), is(0));
    }

    @Test
    public void shouldInit_WithGivenCoords() {
        Coordinates2D xy = new Coordinates2D(42, 43);

        assertThat(xy.getX(), is(42));
        assertThat(xy.getY(), is(43));
    }
}