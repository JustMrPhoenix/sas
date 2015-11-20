package refruity.math.geom.shape;

import org.junit.Assert;
import org.junit.Test;
import refruity.math.geom.shape.Angle;
import refruity.math.geom.shape.Vector;

import static java.lang.Math.PI;

public class AngleTest {
    private static final double EPSILON = 10E-13;

    @Test
    public void testCreateFrom() throws Exception {
        Vector zeroVector = new Vector();
        Angle fromZeroVector = Angle.createFrom(zeroVector);
        Assert.assertEquals(Double.NaN, fromZeroVector.getRadians(), EPSILON);

        Vector topRightVector = new Vector(0.7, 0.7);
        Angle fromTopRightVector = Angle.createFrom(topRightVector);
        Assert.assertEquals(PI / 4, fromTopRightVector.getRadians(), EPSILON);

        Vector topLeftVector = new Vector(-0.7, 0.7);
        Angle fromTopLeftVector = Angle.createFrom(topLeftVector);
        Assert.assertEquals(3.0/4.0 * PI , fromTopLeftVector.getRadians(), EPSILON);

        Vector bottomLeftVector = new Vector(-0.7, -0.7);
        Angle fromBottomLeftVector = Angle.createFrom(bottomLeftVector);
        Assert.assertEquals(5.0/4.0 * PI, fromBottomLeftVector.getRadians(), EPSILON);

        Vector bottomRightVector = new Vector(0.7, -0.7);
        Angle fromBottomRightVector = Angle.createFrom(bottomRightVector);
        Assert.assertEquals(7.0/4.0 * PI, fromBottomRightVector.getRadians(), EPSILON);
    }

    @Test
    public void testGetSector() throws Exception {
        Angle zeroAngle = new Angle();
        Assert.assertEquals(Angle.Sector.TOP_RIGHT, zeroAngle.getSector());

        Angle angle = new Angle(PI / 17 + 0.002);
        Assert.assertEquals(Angle.Sector.TOP_RIGHT, angle.getSector());

        Angle angleInSecondSector = new Angle(0.9 * PI - 0.002);
        Assert.assertEquals(Angle.Sector.TOP_LEFT, angleInSecondSector.getSector());

        Angle angleInThirdSector = new Angle(1.1 * PI + 0.002);
        Assert.assertEquals(Angle.Sector.BOTTOM_LEFT, angleInThirdSector.getSector());

        Angle angleInFourthSector = new Angle(1.9 * PI - 0.002);
        Assert.assertEquals(Angle.Sector.BOTTOM_RIGHT, angleInFourthSector.getSector());
    }

    @Test
    public void testNormalizeRadians() throws Exception {
        Angle zeroAngle = new Angle();
        Assert.assertEquals(0, zeroAngle.normalizeRadians(), EPSILON);

        Angle normalAngle = new Angle(3);
        Assert.assertEquals(3, normalAngle.normalizeRadians(), EPSILON);

        Angle smallNegativeAngle = new Angle(-3);
        Assert.assertEquals(3.2831853071795864, smallNegativeAngle.normalizeRadians(), EPSILON);

        Angle bigPositiveAngle = new Angle(1000);
        Assert.assertEquals(0.9735361584457501, bigPositiveAngle.normalizeRadians(), EPSILON);

        Angle bigNegativeAngle = new Angle(-1000);
        Assert.assertEquals(5.3096491487338363, bigNegativeAngle.normalizeRadians(), EPSILON);
    }
}