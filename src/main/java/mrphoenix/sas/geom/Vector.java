package mrphoenix.sas.geom;

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