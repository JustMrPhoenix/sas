package mrphoenix.sas.geom;

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

    public static Point createFrom(double angle, double radius) {
        return new Point(radius * Math.cos(angle), radius * Math.sin(angle));
    }

    public Point add(Vector v) {
        return new Point(this.x + v.getX(), this.y + v.getY());
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
}
