package refruity.math.geom.shape;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.Random;

import static java.lang.Math.PI;

public class Angle {
    private double radians;

    public Angle() {
        this.radians = 0;
    }

    public Angle(double radians) {
        this.radians = radians;
    }

    public static Angle createFrom(Vector vector) {
        double cosine = vector.getX() / vector.getLength();
        double resultRadians = Math.acos(cosine);

        if (vector.getY() < 0) {
            return new Angle(2*PI - resultRadians);
        }

        return new Angle(resultRadians);
    }

    public static Angle createRandom() {
        return Angle.createRandom(new Random());
    }

    public static Angle createRandom(Random random) {
        return new Angle(2 * PI * random.nextDouble());
    }

    public Sector getSector() {
        double normalizedRadians = this.normalizeRadians();

        if (normalizedRadians < 1.0/2.0 * PI) {
            return Sector.TOP_RIGHT;
        }

        if (normalizedRadians < PI) {
            return Sector.TOP_LEFT;
        }

        if (normalizedRadians < 3.0/2.0 * PI) {
            return Sector.BOTTOM_LEFT;
        }

        if (normalizedRadians < 2.0 * PI) {
            return Sector.BOTTOM_RIGHT;
        }

        throw new InvalidStateException("Normalized angle is bigger than 2*PI");
    }

    public Angle normalizeAngle() {
        return new Angle(this.normalizeRadians());
    }

    public double normalizeRadians() {
        double remainder = this.radians % (2*PI);
        return remainder < 0 ? remainder + (2*PI) : remainder;
    }

    //region generated getters and setters

    public double getRadians() {
        return radians;
    }

    public void setRadians(double radians) {
        this.radians = radians;
    }

    //endregion

    public enum Sector {
        TOP_RIGHT,
        TOP_LEFT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }
}
