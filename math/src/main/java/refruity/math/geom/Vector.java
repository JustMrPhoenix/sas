package refruity.math.geom;

import mrphoenix.sas.util.MathUtil;

public class Vector {
    private double x, y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public Vector multiply(double scalar) {
        return new Vector(scalar * this.x, scalar * this.y);
    }

    public double multiply(Vector vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    public double getLength() {
        //noinspection SuspiciousNameCombination
        return Math.sqrt(MathUtil.square(this.x) + MathUtil.square(this.y));
    }

    //region generated getters and setters

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //endregion
}
