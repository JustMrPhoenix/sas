package mrphoenix.sas.geom;

import org.junit.Test;
import org.junit.Assert;

public class PointTest {
    private static final double EPSILON = 10E-15;

    @Test
    public void testCreateFrom() throws Exception {
        Point zero = Point.createFrom(0, 0);
        Assert.assertEquals(new Point(), zero);

        Point nonZeroTrivial = Point.createFrom(Math.PI/2, 1);
        Point expectedNonZeroTrivial = new Point(0, 1);
        Assert.assertTrue(expectedNonZeroTrivial.squareDistanceTo(nonZeroTrivial) < EPSILON);

        Point nonZeroComplex = Point.createFrom(Math.PI/7, Math.sqrt(3));
        Point expectedNonZeroComplex = new Point(1.560523855244802175, 0.751508680729570617);
        Assert.assertTrue(expectedNonZeroComplex.squareDistanceTo(nonZeroComplex) < EPSILON);
    }

    @Test
    public void testAdd() throws Exception {
        Vector nonZeroVector = new Vector(0.5, 0.5);

        Point zeroPoint = new Point();
        Assert.assertEquals(new Point(0.5, 0.5), zeroPoint.add(nonZeroVector));

        Point nonZeroPoint = new Point(0.2, 0.2);
        Assert.assertEquals(new Point(0.7, 0.7), nonZeroPoint.add(nonZeroVector));
    }
}