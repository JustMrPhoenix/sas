package my.sas.geom;

public class PointDouble {
    private double x, y;

    public PointDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PointDouble add(Vector v) {
        return new PointDouble(v.getX() + this.x, v.getY() + this.y);
    }

    public PointInteger toPointInteger() {
        return new PointInteger((int)this.x, (int)this.y);
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
