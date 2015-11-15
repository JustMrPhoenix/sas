package mrphoenix.sas.geom;

import mrphoenix.sas.util.MathUtil;

public class Point {
    private double x, y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point createFrom(Angle angle, double radius) {
        return Point.createFrom(angle.getRadians(), radius);
    }

    public static Point createFrom(double angleRadians, double radius) {
        return new Point(radius * Math.cos(angleRadians), radius * Math.sin(angleRadians));
    }

    public Point add(Vector v) {
        return new Point(this.x + v.getX(), this.y + v.getY());
    }

    public Vector subtract(Point p) {
        return new Vector(this.x - p.x, this.y - p.y);
    }

    public double distanceTo(Point p) {
        return Math.sqrt(this.squareDistanceTo(p));
    }

    public double squareDistanceTo(Point p) {
        return MathUtil.square(p.x - this.x) + MathUtil.square(p.y - this.y);
    }

    //region integer getters and setters

    public int getIntX() {
        return (int) x;
    }

    public void setIntX(int x) {
        this.x = x;
    }

    public int getIntY() {
        return (int) y;
    }

    public void setIntY(int y) {
        this.y = y;
    }

    //endregion

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


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Point && ((Point) obj).x == this.x && ((Point) obj).y == this.y;
    }
}
