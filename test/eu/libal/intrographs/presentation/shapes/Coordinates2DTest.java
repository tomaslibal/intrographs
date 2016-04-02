package eu.libal.intrographs.presentation.shapes;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
public class Coordinates2DTest {

    private Coordinates2D coords;

    @Before
    public void setUp() throws Exception {
        coords = new Coordinates2D();
    }

    @Test
    public void shouldInitWithVectorZeroZeroWhenNoArgumentsPassedToConstructor() {
        assertThat(coords.getX(), is(0));
        assertThat(coords.getY(), is(0));
    }

    @Test
    public void shouldInitWithGivenCoords() {
        Coordinates2D xy = new Coordinates2D(42, 43);

        assertThat(xy.getX(), is(42));
        assertThat(xy.getY(), is(43));
    }

    @Test
    public void shouldSetCoordinatesToTheGivenValue() {
        Coordinates2D xy = new Coordinates2D(42, 43);

        xy.set(100, 101);

        assertThat(xy.getX(), is(100));
        assertThat(xy.getY(), is(101));
    }

    @Test
    public void shouldInitCoordinatesFromDouble() {
        Coordinates2D xy = new Coordinates2D(1.0, 2.0);

        assertThat(xy.getX(), is(1));
        assertThat(xy.getY(), is(2));
    }
}