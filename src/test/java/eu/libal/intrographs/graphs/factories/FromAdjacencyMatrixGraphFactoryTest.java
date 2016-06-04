package eu.libal.intrographs.graphs.factories;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class FromAdjacencyMatrixGraphFactoryTest {

    @Test(expected = FromAdjacencyMatrixGraphFactory.NotASquareMatrixException.class)
    public void shouldThrowNotASquareMatrixExceptionIfAdjMatrixNotSquare() throws FromAdjacencyMatrixGraphFactory.NotASquareMatrixException {
        int[][] adj = { {1, 2, 3}, {1, 2} };

        FromAdjacencyMatrixGraphFactory.get(adj);
    }

}