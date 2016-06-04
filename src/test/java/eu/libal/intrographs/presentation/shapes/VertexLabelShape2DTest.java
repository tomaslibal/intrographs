package eu.libal.intrographs.presentation.shapes;

import eu.libal.intrographs.common.ListenableField;
import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class VertexLabelShape2DTest {

    @Spy
    VertexShape2D<Integer> vertexShape;

    @Spy
    ListenableField<Coordinates2D> listenableCoords;

    VertexLabelShape2D labelShape;

    private int vertex_x = 10;

    private int vertex_y = 10;

    @Before
    public void setup() {
        VertexShape2D<Integer> vertexShape2D = new VertexShape2D<>(vertex_x, vertex_y, new Vertex<>("vertexShape2D"));
        listenableCoords = Mockito.spy(vertexShape2D.listenableCoords);
        vertexShape = Mockito.spy(vertexShape2D);

        // Swap the listenableCoords field with the Spy
        vertexShape.listenableCoords = listenableCoords;
        labelShape = new VertexLabelShape2D(vertexShape);
    }

    @Test
    public void shouldSubscribeToUpdatesFromTheCoordinatesField() {
        verify(listenableCoords).subscribe(eq("update"), any());
    }

    @Test
    public void shouldUpdateItsCoordinatesWhenVertexShapeUpdatesCoordinates() {
        assertThat(labelShape.getX(), is(vertex_x + VertexLabelShape2D.X_OFFSET));
        assertThat(labelShape.getY(), is(vertex_y + VertexLabelShape2D.Y_OFFSET));

        vertexShape.setX(20.0);
        vertexShape.setY(40.0);

        assertThat(labelShape.getX(), is(20.0 + VertexLabelShape2D.X_OFFSET));
        assertThat(labelShape.getY(), is(40.0 + VertexLabelShape2D.Y_OFFSET));
    }
}