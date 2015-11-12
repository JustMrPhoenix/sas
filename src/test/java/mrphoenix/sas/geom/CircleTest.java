package mrphoenix.sas.geom;

import org.junit.Assert;
import org.junit.Test;

public class CircleTest {

    @Test
    public void testGetRandomPoint() throws Exception {
        Circle circle = new Circle();
        Point randomPoint = circle.getRandomPoint();
        Assert.assertTrue(square(randomPoint.getX()) + square(randomPoint.getY()) < circle.getRadius());
    }

    private static double square(double x) {
        return x * x;
    }
}