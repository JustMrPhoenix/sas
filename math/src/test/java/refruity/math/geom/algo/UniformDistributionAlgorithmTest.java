package refruity.math.geom.algo;

import mrphoenix.sas.util.MathUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import refruity.math.geom.shape.*;

public class UniformDistributionAlgorithmTest extends DistributionAlgorithmTest {
    private DistributionAlgorithm uniformDistributionAlgorithm;

    private static final int ITERATIONS_UNIFORM = 100_000;
    
    @Before
    public void init() {
        uniformDistributionAlgorithm = new UniformDistributionAlgorithm();
        uniformDistributionAlgorithm.setRandomGenerator(RANDOM_GENERATOR);
    }
    
    @Test
    public void testPointsFallWithinCenteredCircle() {
        testPointsFallWithinCircle(new Circle());
    }

    @Test
    public void testPointsFallWithinOffsetCircle() {
        testPointsFallWithinCircle(new Circle(new Point(0.7, 0.7), 1));
    }

    private void testPointsFallWithinCircle(Circle circle) {
        for (int i = 0; i < ITERATIONS; i++) {
            Point randomPoint = uniformDistributionAlgorithm.getRandomPoint(circle);

            double squareDistance = randomPoint.subtract(circle.getCenter()).squareLength();
            double squareRadius = MathUtil.square(circle.getRadius());

            Assert.assertTrue(squareDistance < squareRadius);
        }
    }

    @Test
    public void testPointsFallWithinCenteredDisk() {
        testPointsFallWithinDisk(new Disk(new Point(), 1, 2));
    }

    @Test
    public void testPointsFallWithinOffsetDisk() {
        testPointsFallWithinDisk(new Disk(new Point(0.7, 0.7), 1, 2));
    }

    private void testPointsFallWithinDisk(Disk disk) {
        for (int i = 0; i < ITERATIONS; i++) {
            Point randomPoint = uniformDistributionAlgorithm.getRandomPoint(disk);

            double squareDistance = randomPoint.subtract(disk.getCenter()).squareLength();
            double squareInnerRadius = MathUtil.square(disk.getInnerRadius());
            double squareOuterRadius = MathUtil.square(disk.getOuterRadius());

            Assert.assertTrue(squareInnerRadius < squareDistance);
            Assert.assertTrue(squareDistance < squareOuterRadius);
        }
    }

    @Test
    public void testPointsUniformlyDistributedWithinCenteredDisk() {
        testPointsUniformlyDistributedWithinDisk(new Disk(new Point(), 1, 2));
    }

    @Test
    public void testPointsUniformlyDistributedWithinOffsetDisk() {
        testPointsUniformlyDistributedWithinDisk(new Disk(new Point(0.7, 0.7), 1, 2));
    }

    private void testPointsUniformlyDistributedWithinDisk(Disk disk) {
        // Radius that divides whole disk into 2 disks of equal area
        double borderRadius = Math.sqrt((MathUtil.square(disk.getOuterRadius()) + MathUtil.square(disk.getInnerRadius())) / 2);
        int closePoints = 0, farPoints = 0;

        for (int i = 0; i < ITERATIONS_UNIFORM; i++) {
            Point randomPoint = uniformDistributionAlgorithm.getRandomPoint(disk);

            if (randomPoint.distanceTo(disk.getCenter()) < borderRadius) {
                closePoints++;
            } else {
                farPoints++;
            }
        }

        double ratio = (double) closePoints / (double) farPoints;
        Assert.assertEquals(1, ratio, 0.01);
    }

    @Test
    public void testCenteredCircleSectors() {
        testEverySectorContainsPoint(new Circle());
    }

    @Test
    public void testOffsetCircleSectors() {
        testEverySectorContainsPoint(new Circle(new Point(0.7, 0.7), 1));
    }

    @Test
    public void testCenteredDiskSectors() {
        Disk disk = new Disk(new Point(), 1, 2);
        testEverySectorContainsPoint(disk);
    }

    @Test
    public void testOffsetDiskSectors() {
        Disk disk = new Disk(new Point(0.7, 0.7), 1, 2);
        testEverySectorContainsPoint(disk);
    }

    private void testEverySectorContainsPoint(Shape shape) {
        boolean sectorTopRight = false;
        boolean sectorTopLeft = false;
        boolean sectorBottomLeft = false;
        boolean sectorBottomRight = false;

        for (int i = 0; i < ITERATIONS; i++) {
            Point randomPoint = uniformDistributionAlgorithm.getRandomPoint(shape);
            Angle angle = Angle.createFrom(randomPoint.subtract(shape.getCenter()));
            Angle.Sector sector = angle.getSector();

            if (sector == Angle.Sector.TOP_RIGHT) {
                sectorTopRight = true;
            }

            if (sector == Angle.Sector.TOP_LEFT) {
                sectorTopLeft = true;
            }

            if (sector == Angle.Sector.BOTTOM_LEFT) {
                sectorBottomLeft = true;
            }

            if (sector == Angle.Sector.BOTTOM_RIGHT) {
                sectorBottomRight = true;
            }
        }

        Assert.assertTrue(sectorTopRight);
        Assert.assertTrue(sectorTopLeft);
        Assert.assertTrue(sectorBottomLeft);
        Assert.assertTrue(sectorBottomRight);
    }
}