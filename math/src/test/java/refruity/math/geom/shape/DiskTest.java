package refruity.math.geom.shape;

import mrphoenix.sas.util.MathUtil;
import org.junit.Assert;
import org.junit.Test;
import refruity.math.geom.shape.Disk;
import refruity.math.geom.shape.Point;

import java.util.Random;

public class DiskTest {
    private static final int ITERATIONS = 1000;
    private static final int ITERATIONS_UNIFORM = 100000;

    private static final long SEED = 42;
    private static final Random RANDOM = new Random(SEED);

    private static final Point ZERO = new Point();
    private static final double INNER_RADIUS = 1;
    private static final double OUTER_RADIUS = 2;
    private static final Disk DISK = new Disk(ZERO, INNER_RADIUS, OUTER_RADIUS);

    @Test
    public void testRandomPointInsideDisk() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            Point randomPoint = DISK.getRandomPoint(RANDOM);

            double squareX = MathUtil.square(randomPoint.getX());
            double squareY = MathUtil.square(randomPoint.getY());

            Assert.assertTrue(squareX + squareY < MathUtil.square(OUTER_RADIUS));
            Assert.assertTrue(MathUtil.square(INNER_RADIUS) < squareX + squareY);
        }
    }

    @Test
    public void testRandomPointUniformlyDistributed() throws Exception {
        // Radius that divides whole disk into 2 disks of equal area
        double borderRadius = Math.sqrt((MathUtil.square(DISK.getOuterRadius()) + MathUtil.square(DISK.getInnerRadius())) / 2);
        int closePoints = 0, farPoints = 0;

        for (int i = 0; i < ITERATIONS_UNIFORM; i++) {
            Point randomPoint = DISK.getRandomPoint(RANDOM);

            if (randomPoint.distanceTo(DISK.getCenter()) < borderRadius) {
                closePoints++;
            } else {
                farPoints++;
            }
        }

        double ratio = (double) closePoints / (double) farPoints;
        Assert.assertEquals(1, ratio, 0.01);
    }
}