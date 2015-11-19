package refruity.math.geom;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ShapeTest {
    private static final int ITERATIONS = 1000;

    private static final long SEED = 42;
    private static final Random RANDOM = new Random(SEED);

    @Test
    public void testCircleSectors() throws Exception {
        testEverySectorContainsPoint(new Circle());
    }

    @Test
    public void testDiskSectors() throws Exception {
        Disk disk = new Disk(new Point(), 1, 2);
        testEverySectorContainsPoint(disk);
    }

    private void testEverySectorContainsPoint(Shape shape) throws Exception {
        boolean sectorTopRight = false;
        boolean sectorTopLeft = false;
        boolean sectorBottomLeft = false;
        boolean sectorBottomRight = false;

        for (int i = 0; i < ITERATIONS; i++) {
            Point randomPoint = shape.getRandomPoint(RANDOM);
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