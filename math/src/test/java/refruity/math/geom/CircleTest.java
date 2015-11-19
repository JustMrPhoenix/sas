package refruity.math.geom;

import mrphoenix.sas.util.MathUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class CircleTest {
    private static final int ITERATIONS = 1000;

    private static final long SEED = 42;
    private static final Random RANDOM = new Random(SEED);

    private static final Circle CIRCLE = new Circle();

    @Test
    public void testRandomPointInsideCircle() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            Point randomPoint = CIRCLE.getRandomPoint(RANDOM);

            double squareX = MathUtil.square(randomPoint.getX());
            double squareY = MathUtil.square(randomPoint.getY());
            double squareRadius = MathUtil.square(CIRCLE.getRadius());

            Assert.assertTrue(squareX + squareY < squareRadius);
        }
    }

    @Test
    public void testGetRandomRadiusUniform() throws Exception {
        Random random = new Random(SEED);
        Circle circle = new Circle();

        for (int i = 0; i < 1000; i++) {
            double randomRadius = circle.getRandomRadiusUniform(random);

            Assert.assertTrue(randomRadius < circle.getRadius());
        }
    }
}