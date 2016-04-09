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
        assertThat(coords.getX(), is(0d));
        assertThat(coords.getY(), is(0d));
    }

    @Test
    public void shouldInitWithGivenCoords() {
        Coordinates2D xy = new Coordinates2D(42, 43);

        assertThat(xy.getX(), is(42d));
        assertThat(xy.getY(), is(43d));
    }

    @Test
    public void shouldSetCoordinatesToTheGivenValue() {
        Coordinates2D xy = new Coordinates2D(42, 43);

        xy.set(100, 101);

        assertThat(xy.getX(), is(100d));
        assertThat(xy.getY(), is(101d));
    }

    @Test
    public void shouldInitCoordinatesFromDouble() {
        Coordinates2D xy = new Coordinates2D(1.0, 2.0);

        assertThat(xy.getX(), is(1d));
        assertThat(xy.getY(), is(2d));
    }

    @Test
    public void shouldAddAScalarValueToXY() {
        Coordinates2D xy = new Coordinates2D(1.0, 2.0);

        Coordinates2D result = Coordinates2D.add(xy, 42);

        assertThat(result.getX(), is(43d));
        assertThat(result.getY(), is(44d));
    }

    @Test
    public void shouldAddTwoVectorsTogether() {
        Coordinates2D xy = new Coordinates2D(1.0, 2.0);
        Coordinates2D uv = new Coordinates2D(3.0, 4.0);

        Coordinates2D result = Coordinates2D.add(xy, uv);

        assertThat(result.getX(), is(4d));
        assertThat(result.getY(), is(6d));
    }

    @Test
    public void shouldSubtractTwoVectors() {
        Coordinates2D xy = new Coordinates2D(1.0, 2.0);
        Coordinates2D uv = new Coordinates2D(3.0, 4.0);

        Coordinates2D result = Coordinates2D.sub(xy, uv);

        assertThat(result.getX(), is(-2d));
        assertThat(result.getY(), is(-2d));
    }

    @Test
    public void shouldMultiplyVectorByAScalar() {
        Coordinates2D xy = new Coordinates2D(1.0, 2.0);

        Coordinates2D result = Coordinates2D.mult(xy, 10);

        assertThat(result.getX(), is(10d));
        assertThat(result.getY(), is(20d));
    }

    @Test
    public void shouldReturnADotProduct() {
        Coordinates2D xy = new Coordinates2D(1.0, 2.0);
        Coordinates2D uv = new Coordinates2D(3.0, 4.0);
        Coordinates2D mn = new Coordinates2D(0, 0);
        Coordinates2D st = new Coordinates2D(-1, 1);

        double result1 = Coordinates2D.dot(xy, uv);
        double result2 = Coordinates2D.dot(xy, mn);
        double result3 = Coordinates2D.dot(xy, st);

        assertThat(result1, is(11d));
        assertThat(result2, is(0d));
        assertThat(result3, is(1d));
    }
}